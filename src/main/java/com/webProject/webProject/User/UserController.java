package com.webProject.webProject.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @GetMapping("/owner_check_bno")
    public String owner_check_bno(OwnerCreateForm ownerCreateForm){
        return "owner_check_bno";
    }

    @PostMapping("/owner_check_bno")
    public String owner_check_bno(@Valid OwnerCreateForm ownerCreateForm, BindingResult bindingResult) {
        final String VALID_NUM = "01";
        try{
            String data = String.format("{\"businesses\":[{\"b_no\": \"%s\",\"start_dt\": \"%s\",\"p_nm\": \"%s\"}]}", HtmlUtils.htmlEscape(ownerCreateForm.getB_no()),
                    HtmlUtils.htmlEscape(ownerCreateForm.getStart_dt()), HtmlUtils.htmlEscape(ownerCreateForm.getP_nm()));
            //hhtpClient 객체 생성
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //외부 api 가 존재하는 url
            String url ="https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=6EaPLiF5QZfSCQ1U9%2Fi2OHaHGHjsuguhUI%2FtFqreMA%2F84puk8RRN%2FnJ7sr0h7iU2lnXvsz2oPiHerQg1m%2BlG0g%3D%3D";
            HttpPost httpPost = new HttpPost(url);

            //content-type 정의 및 http body에 json 문자열 정의
            httpPost.addHeader("Content-Type","application/json");
            StringEntity entity = new StringEntity(data, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = httpClient.execute(httpPost, responseHandler);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response, Map.class);

            List<Map> dataList = (List<Map>)map.get("data");
            String result = (String)dataList.get(0).get("valid");

            if(!result.equals(VALID_NUM)){
                return "redirect:/user/owner_check_bno";
            }
        }catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
        return "redirect:/user/owner_signup";
    }

    @GetMapping("/owner_signup")
    public String owner_signup(UserCreateForm userCreateForm){
        return "owner_signup_form";
    }

    @PostMapping("/owner_signup")
    public String owner_signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "owner_signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "owner_signup_form";
        }

        userService.create(userCreateForm.getUserId(), userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getNickname(), "owner");


        return "redirect:/";
    }
    @GetMapping("/user_signup")
    public String user_signup(UserCreateForm userCreateForm){
        return "user_signup_form";
    }
    @PostMapping("/user_signup")
    public String user_signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user_signup_form";
        }
        userService.create(userCreateForm.getUserId(), userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getNickname(), "user");

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup_form";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, UserPasswordForm userPasswordForm, Model model){
        String userId = principal.getName();
        User userinfo = this.userService.getUser(userId);
        model.addAttribute("userinfo", userinfo);

        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/modify")
    public String modifyProfile(Model model, UserUpdateForm userUpdateForm, UserPasswordForm userPasswordForm, Principal principal) {
        String userId = principal.getName();
        User userinfo = this.userService.getUser(userId);
        model.addAttribute("userinfo", userinfo);

        userUpdateForm.setNickname(userinfo.getNickname());
        userUpdateForm.setEmail(userinfo.getEmail());
        userPasswordForm.setPassword(userinfo.getPassword());
        return "update_profile"; // 수정 폼으로 이동합니다.
    }

    // 사용자 프로필 정보 수정 및 비밀번호 변경 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/modify")
    public String modifyProfile(UserUpdateForm userUpdateForm, UserPasswordForm userPasswordForm, Principal principal, BindingResult bindingResult){
        String userId = principal.getName();
        User userinfo = this.userService.getUser(userId);
        if (bindingResult.hasErrors()) {
            return "update_profile";
        }

        // 사용자 정보만 수정하고 비밀번호는 변경하지 않는 경우
        if (userUpdateForm.getNickname() != null || userUpdateForm.getEmail() != null) {
            this.userService.modify(userinfo, userUpdateForm.getNickname(), userUpdateForm.getEmail());
        }

        // 새로운 비밀번호가 입력되었고 사용자 정보가 입력되지 않은 경우, 비밀번호만 변경합니다.
        if (userUpdateForm.getNickname() == null && userUpdateForm.getEmail() == null
                && userPasswordForm.getNewPassword1() != null && !userPasswordForm.getNewPassword1().isEmpty()) {
            if (!userPasswordForm.getNewPassword1().equals(userPasswordForm.getNewPassword2())) {
                bindingResult.rejectValue("password2", "passwordInCorrect",
                        "2개의 패스워드가 일치하지 않습니다.");
                return "update_profile";
            }
            this.userService.modifyPw(userinfo, userPasswordForm.getNewPassword1());
        }

        // 사용자 프로필 페이지로 리다이렉트합니다.
        return "redirect:/user/profile";
    }
}

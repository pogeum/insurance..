package com.webProject.webProject.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
            System.out.println(map.get("status_code"));
            System.out.println(response);

            List<Map> dataList = (List<Map>)map.get("data");
            String result = (String)dataList.get(0).get("valid");
            System.out.println("result : " + result);
            System.out.println("===>" + dataList.get(0).get("valid"));

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
}

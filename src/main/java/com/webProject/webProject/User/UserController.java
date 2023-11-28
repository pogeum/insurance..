package com.webProject.webProject.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/owner_check_bno")
    public String owner_check_bno(OwnerCreateForm ownerCreateForm){
        return "owner_signup_form1";
    }
    @PostMapping("/owner_check_bno")
    public String owner_check_bno(@Valid OwnerCreateForm ownerCreateForm, BindingResult bindingResult) {
        try {
            String data = String.format("{\"b_no\": \"%s\",\"start_dt\": \"%s\",\"p_nm\": \"%s\"}", HtmlUtils.htmlEscape(ownerCreateForm.getB_no()), HtmlUtils.htmlEscape(ownerCreateForm.getStart_dt()), HtmlUtils.htmlEscape(ownerCreateForm.getP_nm()));
            RestTemplate restTemplate = new RestTemplate();
            String serverAUrl = "http://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=6EaPLiF5QZfSCQ1U9%2Fi2OHaHGHjsuguhUI%2FtFqreMA%2F84puk8RRN%2FnJ7sr0h7iU2lnXvsz2oPiHerQg1m%2BlG0g%3D%3D";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> chatRequest = new HttpEntity<>(data, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(serverAUrl, chatRequest, String.class);
            System.out.println(response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
                    String AnswerData = (String) responseMap.get("Answer");
                    String QueryData = (String) responseMap.get("Query");
                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 다른 상태 코드에 대한 처리
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
        return "";
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

        userService.create(userCreateForm.getUserId(), userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getNickname());


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

        userService.create(userCreateForm.getUserId(), userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getNickname());

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


    @PostMapping("/")
    public String sendDataToServer(OwnerCreateForm ownerCreateForm, @PathVariable("nickname") String nickname, @PathVariable("id") Integer id) throws UnsupportedEncodingException {
        try {
            String data = String.format("{\"b_no\": \"%s\",\"start_dt\": \"%s\",\"p_nm\": \"%s\"}", HtmlUtils.htmlEscape(ownerCreateForm.getB_no()), HtmlUtils.htmlEscape(ownerCreateForm.getStart_dt()), HtmlUtils.htmlEscape(ownerCreateForm.getP_nm()));
            RestTemplate restTemplate = new RestTemplate();
            String serverAUrl = "http://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=6EaPLiF5QZfSCQ1U9%2Fi2OHaHGHjsuguhUI%2FtFqreMA%2F84puk8RRN%2FnJ7sr0h7iU2lnXvsz2oPiHerQg1m%2BlG0g%3D%3D";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> chatRequest = new HttpEntity<>(data, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(serverAUrl, chatRequest, String.class);
            System.out.println(response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
                    String AnswerData = (String) responseMap.get("Answer");
                    String QueryData = (String) responseMap.get("Query");
                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 다른 상태 코드에 대한 처리
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
        return "redirect:/chatbotList/" + id;
    }
}

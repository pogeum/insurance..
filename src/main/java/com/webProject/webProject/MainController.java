package com.webProject.webProject;

import com.webProject.webProject.User.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;

@Controller
public class MainController {
    @RequestMapping("/")
    public String root() {
        return "redirect:/store/list";
    }
}

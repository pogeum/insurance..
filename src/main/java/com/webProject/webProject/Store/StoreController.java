package com.webProject.webProject.Store;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class StoreController {
    @GetMapping("/list")
    public String list(Model model){
        return "store_list";
    }
}

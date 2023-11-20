package com.webProject.webProject.Store;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class StoreController {
    @GetMapping("/list")
    public String list(Model model){
        return "store_list";
    }

    @GetMapping("/create")
    public String createStore(Model model){

        return "store_form";
    }

    @PostMapping("/create")
    public String createStore(Model model, StoreForm storeForm){

        return "store_form";
    }
}

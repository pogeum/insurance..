package com.webProject.webProject.Store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping("/list")
    public String list(Model model){
        List<Store> storeList = this.storeRepository.findAll();
        model.addAttribute("storeList", storeList);
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

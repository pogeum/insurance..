package com.webProject.webProject.Store;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;
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

    @PostMapping("/mylocation") // POST 요청을 처리하기 위한 애노테이션 추가
    public String getMyLocation(Model model, @RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude) {
        // 클라이언트의 위치 정보를 가져오는 메서드 호출 (이 부분은 getLocationFromCoordinates 메서드를 호출하는 것으로 가정)
        String location = this.storeService.getLocationFromCoordinates(latitude, longitude);

        List<Store> storeList = this.storeRepository.findAll();
        model.addAttribute("storeList", storeList);
        model.addAttribute("location", location);

        return "store_list";
    }

}

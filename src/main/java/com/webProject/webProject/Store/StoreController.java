package com.webProject.webProject.Store;


import com.webProject.webProject.User.User;
import com.webProject.webProject.User.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    private final StoreRepository storeRepository;


    @GetMapping("/list")
    public String list(Model model){
        List<Store> storeList = this.storeRepository.findAll();
        model.addAttribute("storeList", storeList);
        return "store_list";
    }

    @GetMapping("/create")
    public String createStore(StoreForm storeForm){
        return "store_form";
    }

    @PostMapping("/create")
    public String createStore(Model model, StoreForm storeForm){
        if (storeForm.getFiles()!=null&& !storeForm.getFiles().isEmpty()) {
            storeService.setFiles(storeForm.getFiles());
            this.storeService.createStore(storeForm.getName(),storeForm.getContent(),storeForm.getCategory(),storeForm.getRoadAddress());
        }





        return "redirect:/store/owner/list";
    }

    @GetMapping("/owner/list")
    public String ownerpage_list(Model model) {
//      현 로그인 가게사장 유저 받아와서 걔가 등록한 가게리스트 보내야함 --> 마지막단계
//        User user_owner = userService.getuser . . .  ;
//        List<Store> ownerstore = user_owner.getStoreList();
//        이런식으로.

//      일단 db에 사장님유저, 스토어 하나씩 저장해놈.

        User owner = userService.getUserList().get(0); // 사장
        List<Store> ownerStoreList = storeService.getstoreList_owner(); //가게리스트 일단 사장하나랑만연결된거


        model.addAttribute("ownerStoreList", ownerStoreList);

//        model.addAttribute("getstoreList_owner", this.storeService.getstoreList_owner());
        return "store_owner_list";
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

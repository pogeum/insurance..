package com.webProject.webProject.Store;

import com.webProject.webProject.User.User;
import com.webProject.webProject.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model){
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
}

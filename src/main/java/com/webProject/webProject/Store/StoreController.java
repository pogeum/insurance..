package com.webProject.webProject.Store;


import com.webProject.webProject.CustomUser;
import com.webProject.webProject.Menu.Menu;
import com.webProject.webProject.Menu.MenuForm;
import com.webProject.webProject.Menu.MenuService;
import com.webProject.webProject.Photo.PhotoService;
import com.webProject.webProject.Review.Review;
import com.webProject.webProject.Review.ReviewService;
import com.webProject.webProject.Review_tag.Review_tag;
import com.webProject.webProject.Review_tag.Review_tagRepository;
import com.webProject.webProject.Tag.Tag;
import com.webProject.webProject.User.User;
import com.webProject.webProject.User.UserSecurityService;
import com.webProject.webProject.User.UserService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final PhotoService photoService;
    private final MenuService menuService;
    private final UserSecurityService userSecurityService;
    private final StoreRepository storeRepository;


    @GetMapping("/list")
    public String list(Model model){
        List<Store> storeList = this.storeRepository.findAll();
        model.addAttribute("storeList", storeList);
        return "store_list";
    }

    @GetMapping("/list/my_location")
    public String mylocation_list(Model model, @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude){
        System.out.println(latitude);
        System.out.println(longitude);
        List<Store> storeList = this.storeRepository.findAll();
        model.addAttribute("storeList", storeList);
        return "store_list";
    }

    @GetMapping("/detail/{id}") // 해당 id는 store id
    public String detail(Model model, @PathVariable("id") Integer id) {
        Store store = this.storeService.getStore(id);
        List<Review> reviewList = this.reviewService.getreviewList(store);
        List<Menu> menuList = this.menuService.getstoreMenu(store);

        if (!reviewList.isEmpty()) {
            for (Review review : reviewList) {
                List<Review_tag> tagList = review.getReviewTagList();
                review.setReviewTagList(tagList);
            }
            Review firstReview = reviewList.get(0);
            model.addAttribute("review", firstReview);
        } else {
            Review emptyReview = new Review();
            model.addAttribute("review", emptyReview);
        }

        model.addAttribute("menuList", menuList);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("store", store);
        return "store_detail";
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

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createStore(Model model, StoreForm storeForm, MenuForm menuForm, Principal principal){

        User siteUser = this.userService.getUser(principal.getName());
        return "store_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createStore(Model model, StoreForm storeForm, MenuForm menuForm, BindingResult bindingResult, Principal principal, List<MultipartFile> fileList, @RequestParam("menuName")String[] menuNames) throws Exception{
        User user = this.userService.getUser(principal.getName());

        //fltmxm 메뉴리스트 연결 스토ㅇ어에
        if (bindingResult.hasErrors()) {
            return "store_owner_list";
        }
        List<Menu> menuList = new ArrayList<>();
        for (String menuName : menuNames) {
            Menu menu = new Menu();
            menu.setMenuName(menuName);
            menu.setPrice(100);
            menuList.add(menu);
        }

        Store newStore = storeService.createStore(user, storeForm.getName(),storeForm.getContent(),storeForm.getCategory(),storeForm.getRoadAddress());
        menuService.saveMenus(newStore, menuList);
        newStore.setMenuList(menuList);
//        newStore.setMenuList();
        photoService.saveImgsForStore(newStore, fileList);

        return "redirect:/store/owner/list";
    }

    @GetMapping("/owner/list")
    public String ownerpage_list(Model model,Principal principal) {
        if (principal == null ) {
            return "redirect:/user/login";
        } else {
            User siteUser = this.userService.getUser(principal.getName());
            List<Store> ownerStoreList = storeService.getstoreList_owner(siteUser.getNickname());
            model.addAttribute("ownerStoreList", ownerStoreList);

            return "store_owner_list";
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{storeid}")
    public String modifystore(StoreForm storeForm, @PathVariable("storeid")Integer id, MenuForm menuForm,Principal principal) {
        Store store = storeService.findstoreById(id);
        storeForm.setName(store.getName());
        storeForm.setContent(store.getContent());
        storeForm.setCategory(store.getCategory());
        storeForm.setRoadAddress(store.getRoadAddress());//rnedl굳이호ㅓㄱ인? 리스트 자체가 자기글이자너..사용자확인안핻도댈듯
        return "store_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{storeid}")
    public String modifystore2(StoreForm storeForm, @PathVariable("storeid")Integer id, BindingResult bindingResult, List<MultipartFile> fileList, Principal principal) throws Exception{
        Store store = storeService.findstoreById(id);
        List<MultipartFile> newPhotos = storeForm.getFileList();
        this.photoService.deletePhotosByStore(store);

        if (newPhotos!= null && !newPhotos.isEmpty()) {
            this.photoService.saveImgsForStore(store, newPhotos);
        }
        this.storeService.modifyStore(store, storeForm.getName(), storeForm.getContent(), storeForm.getCategory(), storeForm.getRoadAddress());
//        return "redirect:/store/detail/"+store.getId();
        return "redirect:/store/owner/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{storeid}")
    public String deletestore(@PathVariable("storeid")Integer id,Principal principal) {
        Store store = this.storeService.getStore(id);
        storeService.deleteStore(store);
        return "redirect:/store/owner/list";
    }

//    @GetMapping("/list")
//    public String list(Model model, Principal principal){
//        List<Store> storeList = this.storeRepository.findAll();
//
//        //        현재 이 페이지를 요청한 유저 닉넴 받아오기
//        User siteUser = this.userService.getUser(principal.getName());
////        System.out.println(siteUser.getRole()); ->아직 null
//        System.out.println(siteUser.getNickname());
//
//        model.addAttribute("storeList", storeList);
////        model.addAttribute("requestUser", requestUser);
//        return "store_list";
//    }
}

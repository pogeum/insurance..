package com.webProject.webProject.Store;


import com.webProject.webProject.CustomUser;
import com.webProject.webProject.Menu.Menu;

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

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.ls.LSException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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

    Store tempStore = new Store();
    List<Menu> tempmenuList = new ArrayList<>();

    @GetMapping("/list")
    public String list(Model model){
        List<Store> storeList = this.storeRepository.findAll();
        model.addAttribute("storeList", storeList);
        return "store_list";
    }

    @GetMapping("/list/findStores")
    public ResponseEntity<List<Store>> findStoresByAddress(Model model, @RequestParam("jibunAddress") String jibunAddress) {
        List<Store> storeList  = this.storeService.getAddressList(jibunAddress);
        model.addAttribute("storeList", storeList);

        return ResponseEntity.ok(storeList);
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
    public String createStore(StoreForm storeForm,Principal principal, Model model){

        Store newstore = storeService.defaultStore(userService.getUser(principal.getName()));

//        tempStore.setMenuList(tempmenuList);
//        User siteUser = this.userService.getUser(principal.getName()); // ?이거왜있는거
        model.addAttribute("store",newstore);
//        model.addAttribute("process", "create");
        System.out.println(newstore.getId());
        return "store_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createStore(Model model, StoreForm storeForm, BindingResult bindingResult, Principal principal, List<MultipartFile> fileList) throws Exception{
        User user = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            return "store_owner_list";
        }
        Store newStore = storeService.createStore(user, storeForm.getName(),storeForm.getContent(),storeForm.getCategory(),storeForm.getRoadAddress());
        menuService.saveMenus(newStore,tempmenuList);
        newStore.setMenuList(tempmenuList);
        photoService.saveImgsForStore(newStore, fileList);
        tempmenuList.clear();
        return "redirect:/store/owner/list";
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/addmenu")
//    public String addmenu(MenuForm menuForm, @RequestParam String process, Model model) {
//        model.addAttribute("process", process);
//        return "menu_form";
//    }
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/addmenu") //하나씩만됨..
//    public String addmenu(MenuForm menuForm, Principal principal, @RequestParam String process) {
//
//        Menu menu = new Menu();
//        menu.setMenuName(menuForm.getMenuName());
//        menu.setPrice(menuForm.getPrice());
//        tempmenuList.add(menu);
//
//        if (process.equals("create")) {
//            return "redirect:/store/create";
//        } else {
//            return "redirect:/store/" + process ;
//        }
//    }

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
    public String modifystore(Model model, StoreForm storeForm, @PathVariable("storeid")Integer id ,Principal principal) {
        Store store = storeService.findstoreById(id);
        storeForm.setName(store.getName());
        storeForm.setContent(store.getContent());
        storeForm.setCategory(store.getCategory());
        storeForm.setPostcode(store.getPostcode());
        storeForm.setRoadAddress(store.getRoadAddress());
        storeForm.setJibunAddress(store.getJibunAddress());
        storeForm.setMenuList(store.getMenuList());
        model.addAttribute("store",store);
        model.addAttribute("process", "modify/"+ store.getId());
        return "store_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{storeid}")
    public String modifystore(StoreForm storeForm ,@PathVariable("storeid")Integer id, BindingResult bindingResult, List<MultipartFile> fileList, Principal principal) throws Exception{
        Store store = storeService.findstoreById(id);

        photoService.saveImgsForStore(store, fileList);


        List<MultipartFile> newPhotos = storeForm.getFileList();
//        this.photoService.deletePhotosByStore(store);//수정안할수도있는데 지워버리면..;;

        if (newPhotos!= null && !newPhotos.isEmpty()) {
            this.photoService.saveImgsForStore(store, newPhotos);
        }

        menuService.saveMenus(store,tempmenuList);
        store.setMenuList(tempmenuList);
        this.storeService.modifyStore(store, storeForm.getName(), storeForm.getContent(), storeForm.getCategory(), storeForm.getPostcode(), storeForm.getRoadAddress(), storeForm.getJibunAddress());
        tempmenuList.clear();
        return "redirect:/store/owner/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{storeid}")
    public String deletestore(@PathVariable("storeid")Integer id,Principal principal) {
        Store store = this.storeService.getStore(id);
        storeService.deleteStore(store);
        return "redirect:/store/owner/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/menuList/{storeid}")
    public String menuList(@PathVariable(name = "storeid") Integer storeid, Model model) {
        Store store = storeService.findstoreById(storeid);
        List<Menu> menuList = menuService.getstoreMenu(store);
        model.addAttribute("menuList", menuList);
        model.addAttribute("store",store);
        return "menu_list";
    }


}

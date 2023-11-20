package com.webProject.webProject.Review;

import com.webProject.webProject.Store.Store;
import com.webProject.webProject.User.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final StoreService storeService;
    private final ReviewService reviewService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}") //사용자가 "리뷰작성하기"버튼 클릭하면 실행할 메서드. createReview템플릿 전달함.
    public String createReview(ReviewForm reviewForm, @PathVariable("id") Integer id) {
        return "review_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}") //사용자가 get으로 받은 위 페이지에, 데이터 입력후 "저장하기" 버튼 클릭하면 실행할 메서드.
    public String createReview(Model model, @PathVariable("id") Integer id, @Valid ReviewForm reviewForm, Principal principal, BindingResult bindingResult){
        Store store = this.storeService.getstoreById(id);
        User user = this.userService.getUser(principal.getName());
        // 사용자가 보낸 데이터 reviewForm 형식으로 받아옴
        if (bindingResult.hasErrors()) {
            model.addAttribute("store", store);
            return "review_form";
        }
        this.reviewService.create(user, store, reviewForm.getContent(), reviewForm.getRating(), LocalDateTime.now());
        return String.format("redirect:/restaurant/detail/%s", id);
    }

    @GetMapping("/list")
    public String reviewlist(Model model) {
        List<Review> reviewList = reviewService.getreviewList();
        model.addAttribute("reviewList" , reviewList);
        return "review_list";
    }

    @GetMapping("/detail/{reviewid}")
    public String reviewdetail(Model model, @PathVariable("reviewid") Integer reviewid) {
        Review targetReview = reviewService.findById(reviewid);
        model.addAttribute("targetReview" , targetReview);
        return "review_detail";
    }

}

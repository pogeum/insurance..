package com.TeamProject.TeamProject.Review;


import com.TeamProject.TeamProject.Member.Member;
import com.TeamProject.TeamProject.Member.MemberRepository;
import com.TeamProject.TeamProject.Restaurant.Restaurant;
import com.TeamProject.TeamProject.Restaurant.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    private final MemberRepository memberRepository; // ㅌㅔ스트용. 추후 삭제예정
    private final ReviewRepository reviewRepository; // 테스트용. 추후 삭제예정
    @GetMapping("/create") //사용자가 "리뷰작성하기"버튼 클릭하면 실행할 메서드. createReview템플릿 전달함.
    public String createReview(ReviewForm reviewForm) {
        return "createReview_form";
    }
    @PostMapping("/create") //사용자가 get으로 받은 위 페이지에, 데이터 입력후 "저장하기" 버튼 클릭하면 실행할 메서드.
    public String createReview(Model model, @Valid ReviewForm reviewForm, BindingResult bindingResult){
        // 사용자가 보낸 데이터 reviewForm 형식으로 받아옴

        if (bindingResult.hasErrors()) {
            return "createReview_form";
        }

//        reviewForm.setCreateDate(LocalDateTime.now());//
//        reviewForm.setRestaurantId(3);//  ----------> db에 저장되는지만 확인하기 위한 테스트용 코드. 지워도됨.

        Restaurant restaurant = restaurantService.getRestaurantById(reviewForm.getRestaurantId()); // 해당 식당 아이디 찾아서 반환
        reviewService.create(restaurant, reviewForm.getContent(),reviewForm.getRating(),reviewForm.getCreateDate());
        model.addAttribute("restaurant",restaurant);
        return String.format("redirect:/restaurant/detail/%s", reviewForm.getRestaurantId());
    }

    @GetMapping("/list")
    public String reviewlist(Model model) {
        List<Review> reviewList = reviewService.getreviewList();
        model.addAttribute("reviewList" , reviewList);
        return "review_list";
    }


    @GetMapping("/detail")
    public String reviewdetail(Model model, Integer reviewid) {
        Review targetReview = new Review();
//        Restaurant restaurant = new Restaurant();
//
//        Member member = new Member();
//        member.setMemberId("김포도");
//        member.setEmail("test@test.com");
//        member.setPassword("password");
//        this.memberRepository.save(member);
//
//        Review targetReview = new Review();
//        targetReview.setAuthor(member);
//        targetReview.setRating(4);
//        targetReview.setContent("테스트용");
//        targetReview.setRestaurant(restaurant);
//        targetReview.setThumbsup(3);
//        this.reviewRepository.save(targetReview);

        model.addAttribute("targetReview" , targetReview);
        return "review_detail";
    }
}

package com.TeamProject.TeamProject.Review;


import com.TeamProject.TeamProject.Member.Member;
import com.TeamProject.TeamProject.Member.MemberRepository;
import com.TeamProject.TeamProject.Restaurant.Restaurant;
import com.TeamProject.TeamProject.Restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/create/{id}")
    public String createReview(Model model, @PathVariable("id")Integer id, @RequestParam String content,
                                @RequestParam int rating, LocalDateTime localDateTime){
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        reviewService.create(restaurant, content, rating, localDateTime);

        return String.format("redirect:/restaurant/home/%s", id);
    }

    @GetMapping("/detail")
    public String reviewdetail(Model model, Integer reviewid) {

        Restaurant restaurant = new Restaurant();

        Member member = new Member();
        member.setMembername("김포도");
        member.setEmail("test@test.com");
        member.setPassword("password");
        this.memberRepository.save(member);

        Review targetReview = new Review();
        targetReview.setAuthor(member);
        targetReview.setRating(4);
        targetReview.setContent("테스트용");
        targetReview.setRestaurant(restaurant);
        targetReview.setThumbsup(3);
        this.reviewRepository.save(targetReview);

        model.addAttribute("targetReview" , targetReview);
        return "review_detail";
    }
}

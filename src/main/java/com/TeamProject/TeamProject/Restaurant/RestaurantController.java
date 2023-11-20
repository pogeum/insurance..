package com.TeamProject.TeamProject.Restaurant;

import com.TeamProject.TeamProject.Restaurant.Restaurant;
import com.TeamProject.TeamProject.Restaurant.RestaurantService;
import com.TeamProject.TeamProject.Review.Review;
import com.TeamProject.TeamProject.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Restaurant> paging = this.restaurantService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "Restaurant_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        List<Review> reviews = this.reviewService.getReviewByRestaurantId(id); // 해당 레스토랑의 모든 리뷰 가져오기
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reviews", reviews); // 리뷰 리스트를 모델에 추가

        return "restaurant_detail";
    }
}

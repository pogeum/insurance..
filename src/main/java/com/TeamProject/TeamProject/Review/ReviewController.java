package com.TeamProject.TeamProject.Review;


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

  @PostMapping("/create/{id}")
  public String createReview(Model model, @PathVariable("id")Integer id, @RequestParam String content,
                             @RequestParam String title, @RequestParam int rating, LocalDateTime localDateTime){
    Restaurant restaurant = restaurantService.getRestaurantById(id);
    reviewService.create(restaurant, content, title, rating, localDateTime);

    return String.format("redirect:/restaurant/home/%s", id);
  }

}

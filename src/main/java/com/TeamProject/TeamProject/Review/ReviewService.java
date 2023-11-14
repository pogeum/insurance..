package com.TeamProject.TeamProject.Review;


import com.TeamProject.TeamProject.Restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public void create(Restaurant restaurant, String content, String title, int rating, LocalDateTime localDateTime) {
    Review review = new Review();
    review.setRestaurant(restaurant);
    review.setContent(content);
    review.setTitle(title);
    review.setRating(rating);
    review.setCreateDate(localDateTime);

    reviewRepository.save(review);
  }
  // 다양한 검색 및 정렬 옵션에 따라 리뷰 목록을 가져오는 메소드


}

package com.TeamProject.TeamProject.Review;

import com.TeamProject.TeamProject.Restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review findById(Integer reviewid) {
        return this.reviewRepository.findById(reviewid).get();
    }

    public void create(Restaurant restaurant, String content, int rating, LocalDateTime localDateTime) {
        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setContent(content);
        review.setRating(rating);
        review.setCreateDate(localDateTime);

        reviewRepository.save(review);
    }

}
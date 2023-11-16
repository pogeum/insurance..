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

    public void create(Restaurant restaurant,String content, double rating, LocalDateTime localDateTime) {
        Review review = new Review();
        review.setRestaurant(restaurant);
//        review.setThumbsup(5);   --------------> x테스트용. 추후 삭제예정
        review.setContent(content);
        review.setRating(rating);
        review.setCreateDate(localDateTime);

        this.reviewRepository.save(review);
    }

}
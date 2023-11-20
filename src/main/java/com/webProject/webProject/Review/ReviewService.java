package com.webProject.webProject.Review;

import com.webProject.webProject.Store.Store;
import com.webProject.webProject.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getreviewList() {
        return this.reviewRepository.findAll();
    }
    public Review findById(Integer reviewid) {
        return this.reviewRepository.findById(reviewid).get();
    }

    public void create(User user, Store store, String content, double rating, LocalDateTime localDateTime) {
//        String author = member.map(Member::getNickname).orElse("비회원");
//        String restaurantname = restaurant.map(Restaurant::getTitle).orElse("식당데이터 아직없음");

        Review review = new Review();
        review.setStore(store);
        review.setAuthor(user);        review.setContent(content);
        review.setRating(rating)
        ;
        review.setCreateDate(localDateTime);

        this.reviewRepository.save(review);
    }

    public List<Review> getReviewBystordId(Integer restaurantId) {
        return reviewRepository.findBystoreId(restaurantId);
    }
}

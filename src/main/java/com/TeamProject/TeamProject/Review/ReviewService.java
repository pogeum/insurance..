package com.TeamProject.TeamProject.Review;

import com.TeamProject.TeamProject.Member.Member;
import com.TeamProject.TeamProject.Member.MemberRepository;
import com.TeamProject.TeamProject.Restaurant.Restaurant;
import com.TeamProject.TeamProject.Restaurant.RestaurantRepository;
import com.TeamProject.TeamProject.Restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Review> getreviewList() {
        return this.reviewRepository.findAll();
    }
    public Review findById(Integer reviewid) {
        return this.reviewRepository.findById(reviewid).get();
    }

    public void create(Member member, Restaurant restaurant, String content, double rating, LocalDateTime localDateTime) {


//        String author = member.map(Member::getNickname).orElse("비회원");
//        String restaurantname = restaurant.map(Restaurant::getTitle).orElse("식당데이터 아직없음");

        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setAuthor(member);
        review.setContent(content);
        review.setRating(rating);
        review.setCreateDate(localDateTime);

        this.reviewRepository.save(review);
    }

}
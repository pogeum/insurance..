package com.TeamProject.TeamProject.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 안녕하세요
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByRestaurantId(Integer restaurant);
}

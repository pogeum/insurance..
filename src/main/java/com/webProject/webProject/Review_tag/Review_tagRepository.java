package com.webProject.webProject.Review_tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Review_tagRepository extends JpaRepository<Review_tag, Integer> {
    List<Review_tag> findByReviewId(Integer reviewId);
}

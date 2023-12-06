package com.webProject.webProject.Photo;

import com.webProject.webProject.Review.Review;
import com.webProject.webProject.Review_tag.Review_tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByReviewId(Integer reviewId);

    Photo findStoreById(Integer id);
}

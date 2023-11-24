package com.webProject.webProject.Review;

import com.webProject.webProject.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByStore(Store store);
}

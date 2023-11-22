package com.webProject.webProject.Review;

import com.webProject.webProject.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findBystoreId(Integer restaurant);
    List<Review> findAllByStore(Store store);
}

package com.TeamProject.TeamProject.Restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Page<Restaurant> findAll(Pageable pageable);
    Page<Restaurant> findAll(Specification<Restaurant> spec, Pageable pageable);

}

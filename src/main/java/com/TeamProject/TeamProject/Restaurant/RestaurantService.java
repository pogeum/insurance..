package com.TeamProject.TeamProject.Restaurant;

import com.TeamProject.TeamProject.DataNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> getRestaurant() {
        return this.restaurantRepository.findAll();
    }

    public Page<Restaurant> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, 3);
        Specification<Restaurant> spec = search(kw);
        return this.restaurantRepository.findAll(spec, pageable);
    }

    public Restaurant getRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = this.restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new DataNotFoundException("restaurant not found");
        }
    }

    private Specification<Restaurant> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Restaurant> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거

                return cb.or(
                        cb.like(q.get("title"), "%" + kw + "%"), // 제목
                        cb.like(q.get("address"), "%" + kw + "%") // 도로 주소
                );
            }
        };
    }
}

package com.TeamProject.TeamProject.Restaurant;

import com.TeamProject.TeamProject.DataNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Restaurant> getList(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        return this.restaurantRepository.findAll(pageable);
    }

    public Restaurant getRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = this.restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new DataNotFoundException("restaurant not found");
        }
    }
}

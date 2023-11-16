package com.TeamProject.TeamProject.Naver;

import com.TeamProject.TeamProject.Restaurant.Restaurant;
import com.TeamProject.TeamProject.Restaurant.RestaurantRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

@Controller
@RequiredArgsConstructor
public class NaverController {
    private final RestaurantRepository restaurantRepository;

    private static final Logger log = (Logger) LoggerFactory.getLogger(NaverController.class);

    @GetMapping("/naver/{title}")
    public String naver(@PathVariable("title") String title){
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query",title)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        // 헤더 추가 위해
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "P5UyFtfOmhKKp9jOUfcg")
                .header("X-Naver-Client-Secret", "lGZXd7sXhR")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        saveJsonToDatabase(result.getBody());

        return result.getBody();
    }
    private void saveJsonToDatabase(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            for (JsonNode item : jsonNode.get("items")) {
                Restaurant restaurant = new Restaurant();
                restaurant.setTitle(item.get("title").asText());
                restaurant.setCategory(item.get("category").asText());
                restaurant.setAddress(item.get("address").asText());
                restaurant.setRoadAddress(item.get("roadAddress").asText());

                restaurantRepository.save(restaurant);
            }

            log.info("Data saved to the database");
        } catch (IOException e) {
            log.error("Error saving data to the database", e);
        }
    }
}
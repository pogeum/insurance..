package com.TeamProject.TeamProject.Controller;

import ch.qos.logback.classic.Logger;
import com.TeamProject.TeamProject.Restaurant.RestaurantRepository;
import com.TeamProject.TeamProject.Restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final RestaurantService restaurantService;

    // Declare a logger
    private static final Logger log = (Logger) LoggerFactory.getLogger(NaverController.class);

    @GetMapping("/naver")
    public String naver(){
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query","대전 서구 탄방동 맛집")
                .queryParam("display", 100)
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
        saveJsonToFile(result.getBody());
        restaurantService.saveJsonToDatabase(result.getBody());

        return result.getBody();
    }

    private void saveJsonToFile(String jsonData) {
        try {
            // Specify the file path where you want to save the JSON data on the desktop
            String desktopPath = System.getProperty("user.home") + "/Desktop";
            String filePath = desktopPath + "/naver_search_result.text";

            // Create a File object
            File file = new File(filePath);

            // Create a BufferedWriter to write data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write the JSON data to the file
                writer.write(jsonData);
            }

            log.info("JSON data saved to file: {}", filePath);
        } catch (IOException e) {
            log.error("Error saving JSON data to file", e);
        }
    }
}

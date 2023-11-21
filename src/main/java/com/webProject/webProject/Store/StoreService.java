package com.webProject.webProject.Store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class StoreService {
    public String getLocationFromCoordinates(String latitude, String longitude) {
        // 여기서는 가정적으로 위도와 경도를 사용하여 위치 정보를 반환하는 방식을 작성합니다.
        // 실제로는 외부 API를 호출하여 해당 위도와 경도에 대한 위치 정보를 가져와야 합니다.

        // 예시로 위도와 경도를 합쳐서 위치 정보를 생성하는 방식을 사용합니다.
        String location = "위도: " + latitude + ", 경도: " + longitude;

        return location;
    }
}

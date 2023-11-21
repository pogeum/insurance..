package com.webProject.webProject.Store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private List<MultipartFile> files;

    public List<Store> getstoreList_owner() {
        return this.storeRepository.findAll();
    }

    public void setFiles(List<MultipartFile> files) {
        this.files=files;
    }

    public List<byte[]> convertMultipartFIleToByteArray() throws IOException {
        List<byte[]> bytes = new ArrayList<>();
        for (MultipartFile file : this.files) {
            bytes.add(file.getBytes());
        }

        return bytes;
    }

    public void createStore(String name, String content, String category, String roadAddress) {
        Store store = new Store();
        store.setName(name);
        store.setContent(content);
        store.setCategory(category);
        store.setRoadAddress(roadAddress);
        store.setCreateDate(LocalDateTime.now());

        try {
            if (this.files !=null && ! this.files.isEmpty()) {
                store.setImagefiles(this.convertMultipartFIleToByteArray());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }


        this.storeRepository.save(store);
    }



    public String getLocationFromCoordinates(String latitude, String longitude) {
        // 여기서는 가정적으로 위도와 경도를 사용하여 위치 정보를 반환하는 방식을 작성합니다.
        // 실제로는 외부 API를 호출하여 해당 위도와 경도에 대한 위치 정보를 가져와야 합니다.

        // 예시로 위도와 경도를 합쳐서 위치 정보를 생성하는 방식을 사용합니다.
        String location = "위도: " + latitude + ", 경도: " + longitude;

        return location;
    }

}

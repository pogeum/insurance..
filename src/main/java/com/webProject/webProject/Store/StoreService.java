package com.webProject.webProject.Store;

import com.webProject.webProject.DataNotFoundException;
import com.webProject.webProject.User.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.PageFormat;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private List<MultipartFile> files;

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

    public Store defaultStore(User user) {
        Store store = new Store();
        store.setAuthor(user);
        return this.storeRepository.save(store);
    }


    public Store createStore(User user, String name, String content, String category, String roadAddress, String jibunAddress) {
        Store store = new Store();
        store.setAuthor(user);
        store.setName(name);
        store.setContent(content);
        store.setCategory(category);
        store.setRoadAddress(roadAddress);
        store.setJibunAddress(jibunAddress);
        store.setCreateDate(LocalDateTime.now());
        return this.storeRepository.save(store);
    }


    public Store getStore(Integer id) {
        Optional<Store> store = this.storeRepository.findById(id);
        if (store.isPresent()) {
            return store.get();
        } else {
            throw new DataNotFoundException("store not found");
        }
    }

    public String getLocationFromCoordinates(String latitude, String longitude) {
        // 여기서는 가정적으로 위도와 경도를 사용하여 위치 정보를 반환하는 방식을 작성합니다.
        // 실제로는 외부 API를 호출하여 해당 위도와 경도에 대한 위치 정보를 가져와야 합니다.

        // 예시로 위도와 경도를 합쳐서 위치 정보를 생성하는 방식을 사용합니다.
        String location = "위도: " + latitude + ", 경도: " + longitude;

        return location;
    }
    public Store findstoreById(Integer id) {
        return this.storeRepository.findById(id).get();
    }


    public void modifyStore(Store store, String name, String content, String category, String postcode, String roadAddress, String jibunAddress) {
        store.setName(name);
        store.setContent(content);
        store.setCategory(category);
        store.setPostcode(postcode);
        store.setRoadAddress(roadAddress);
        store.setJibunAddress(jibunAddress);
        store.setCreateDate(LocalDateTime.now());
        this.storeRepository.save(store);
    }

    public void deleteStore(Store store) {
        this.storeRepository.delete(store);
    }

    public List<Store> getAddressList(String jibunAddress) {
        String[] addressParts = jibunAddress.split("\\s+"); // 공백을 기준으로 문자열 분할
        String formattedAddress = String.join(" ", Arrays.copyOfRange(addressParts, 0, 3)); // 처음 세 부분을 결합

        Specification<Store> spec = search(formattedAddress); // 형식화된 주소 사용
        return this.storeRepository.findAllByKeyword(formattedAddress);
    }

    private Specification<Store> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Store> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                return cb.like(q.get("jibunAddress"), "%" + kw + "%"); // 제목
            }
        };
    }


    public List<Store> getstoreList_owner(String authorname) {
        List<Store> targetstoreList = new ArrayList<>();
        for (Store store : this.storeRepository.findAll()){
            if (store.getAuthor().getNickname() == null ) {
                store.getAuthor().setNickname("unknown");
            }

            if (store.getAuthor().getNickname().equals(authorname)) {
                targetstoreList.add(store);
            }
        }
        return targetstoreList;
    }
//    페이징처리 메서드
    public Page<Store> getList(int page, User author) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));

//        List<Store> ownerstore = getstoreList_owner(nickname);

        return this.storeRepository.findAllById(author.getId(), pageable);
    }

    public Page<Store> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.storeRepository.findAll(pageable);
    }



    public Page<Store> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        return this.storeRepository.findStoreByKeyword(kw, pageable);
    }


//    // 데이터베이스에서 음식점 목록을 가져오는 메서드
//    public List<Store> getRestaurantsNearby(double userLatitude, double userLongitude) {
//        // 데이터베이스에서 모든 음식점 정보를 가져옴
//        List<Store> allStores = storeRepository.getAllStores();
//        List<Store> nearbyStores = new ArrayList<>();
//
//        // 사용자 위치와 음식점 위치를 기반으로 거리를 계산하여 3km 이내의 음식점을 선택
//        for (Store store : allStores) {
//            double distance = calculateDistance(userLatitude, userLongitude, store.getLatitude(), store.getLongitude());
//            if (distance <= 3.0) {
//                nearbyStores.add(store);
//            }
//        }
//
//        return nearbyStores;
//    }
//
//    // 두 지점 간의 거리를 계산하는 메서드 (Haversine 공식 활용)
//    private double calculateDistance(double x1, double y1, double x2, double y2) {
//        double distance;
//        double radius = 6371; // 지구 반지름(km)
//        double toRadian = Math.PI / 180;
//
//        double deltaLatitude = Math.abs(x1 - x2) * toRadian;
//        double deltaLongitude = Math.abs(y1 - y2) * toRadian;
//
//        double sinDeltaLat = Math.sin(deltaLatitude / 2);
//        double sinDeltaLng = Math.sin(deltaLongitude / 2);
//        double squareRoot = Math.sqrt(
//                sinDeltaLat * sinDeltaLat +
//                        Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);
//
//        distance = 2 * radius * Math.asin(squareRoot);
//
//        return distance;
//    }
}

package com.webProject.webProject.Store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
//    List<Store> findNearbyStores(String userAddress);
}

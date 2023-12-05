package com.webProject.webProject.Store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findByjibunAddressContaining(String jibunAddress);

    @Query("select distinct s from Store s where s.jibunAddress like %:kw%")
    List<Store> findAllByKeyword(@Param("kw") String kw);
}

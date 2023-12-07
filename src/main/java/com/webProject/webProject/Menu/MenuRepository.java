package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT m FROM Menu m WHERE m.menuName LIKE %:keyword%")
    List<Menu> findMenusByKeyword(@Param("keyword") String keyword);
}

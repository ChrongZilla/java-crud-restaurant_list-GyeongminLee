package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import java.util.List;

public interface RestaurantRepository {
    void save(Restaurant restaurant);                       // 등록
    List<Restaurant> findAll();                             // 전체 조회
    Restaurant findById(Long id);                           // 단일 조회
    boolean update(Restaurant restaurant);                  // 수정
    boolean delete(Long id);                                // 삭제
    List<Restaurant> findByKeyword(String keyword);         // 이름(restaurantName) 검색
    List<Restaurant> findByCategory(String category);       // 업종 검색
}
package com.example.restaurant.service;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import java.util.List;
import java.util.Objects;

public class RestaurantService {
    private final RestaurantRepository repository;  // 변하지 않으니 final

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public void save(Restaurant restaurant) {
        // 1. 이름 빈 값 검증
        if(restaurant.getRestaurantName() == null || restaurant.getRestaurantName().isBlank())
            throw new IllegalArgumentException("식당 이름은 비어있을 수 없습니다.");

        // 2. category 값 검증
        if(!(restaurant.getCategory().equals("일반음식점") || restaurant.getCategory().equals("휴게음식점") || restaurant.getCategory().equals("제과점영업")))
            throw new IllegalArgumentException("잘못된 업종명입니다.");

        // 3. 중복 등록 방지
        List<Restaurant> all = repository.findAll();
        for (Restaurant r : all) {
            boolean sameName = r.getRestaurantName().equals(restaurant.getRestaurantName());
            boolean sameAddress = Objects.equals(r.getAddress(), restaurant.getAddress());
            if (sameName && sameAddress) {
                throw new IllegalArgumentException("이미 등록된 맛집입니다.");
            }
        }

        repository.save(restaurant);
    }

    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    public Restaurant findById(Long id) {
        return repository.findById(id);
    }

    public boolean update(Restaurant restaurant) {
        // 1. 이름 빈 값 검증
        if (restaurant.getRestaurantName() == null || restaurant.getRestaurantName().isBlank())
            throw new IllegalArgumentException("식당 이름은 비어있을 수 없습니다.");

        // 2. category 값 검증
        if (!(restaurant.getCategory().equals("일반음식점") || restaurant.getCategory().equals("휴게음식점") || restaurant.getCategory().equals("제과점영업")))
            throw new IllegalArgumentException("잘못된 업종명입니다.");

        // 3. 중복 등록 방지 (자기 자신 제외)
        List<Restaurant> all = repository.findAll();
        for (Restaurant r : all) {
            if (r.getId().equals(restaurant.getId())) continue;  // 자기 자신은 스킵

            boolean sameName = r.getRestaurantName().equals(restaurant.getRestaurantName());
            boolean sameAddress = Objects.equals(r.getAddress(), restaurant.getAddress());
            if (sameName && sameAddress) {
                throw new IllegalArgumentException("이미 등록된 맛집입니다.");
            }
        }

        return repository.update(restaurant);
    }

    public boolean delete(Long id) {
        return repository.delete(id);
    }

    public List<Restaurant> findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    public List<Restaurant> findByCategory(String category) {
        return repository.findByCategory(category);
    }
}
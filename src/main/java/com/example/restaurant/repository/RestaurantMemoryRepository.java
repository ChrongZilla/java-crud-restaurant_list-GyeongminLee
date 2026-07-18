package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantMemoryRepository implements RestaurantRepository {
    Scanner sc = new Scanner(System.in);
    private List<Restaurant> restaurants = new ArrayList<>();
    private Long nextId = 1L;   // 다음에 부여할 id

    @Override
    public void save(Restaurant restaurant) {
        // restaurant.setId(nextId)로 id 부여 후 nextId 증가
        restaurant.setId(nextId++);
        // restaurants 리스트에 추가
        restaurants.add(restaurant);
    }

    @Override
    public List<Restaurant> findAll() {
        // restaurants 전체 반환
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        // 반복문: id 일치하는 것 찾아서 반환, 없으면 null 반환
        for(Restaurant r : restaurants)
            if(r.getId().equals(id))    // Long은 래퍼 클래스(객체)임
                return r;
        return null;
    }

    @Override
    public boolean update(Restaurant restaurant) {
        // findById로 기존 객체 찾아서, 있으면 필드 값 덮어쓰고 true 반환, 없으면 false 반환
        Restaurant target = findById(restaurant.getId());
        if (target == null) return false;

        // target의 setter들 호출해서 restaurant의 값으로 갱신
        else {
            target.setCategory(restaurant.getCategory());
            target.setRestaurantName(restaurant.getRestaurantName());
            target.setAddress(restaurant.getAddress());
            target.setPhoneNumber(restaurant.getPhoneNumber());
            return true;
        }
    }

    @Override
    public boolean delete(Long id) {
        // TODO: 해당 id를 가진 객체를 리스트에서 제거, 성공하면 true 반환
        return restaurants.removeIf(r -> r.getId().equals(id));
    }

    @Override
    public List<Restaurant> findByKeyword(String keyword) {
        // TODO: restaurantName에 keyword가 포함된 것들만 걸러서 반환
        return null;
    }

    @Override
    public List<Restaurant> findByCategory(String category) {
        // TODO: category가 일치하는 것들만 걸러서 반환
        return null;
    }
}
package com.example.restaurant.service;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import java.util.List;

public class RestaurantService {
    private final RestaurantRepository repository;  // 변하지 않으니 final

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public void save(Restaurant restaurant) {
        repository.save(restaurant);
    }

    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    public Restaurant findById(Long id) {
        return repository.findById(id);
    }

    public boolean update(Restaurant restaurant) {
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
package com.example.restaurant;

import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.RestaurantMemoryRepository;
import com.example.restaurant.repository.RestaurantFileRepository;
import com.example.restaurant.repository.RestaurantDBRepository;
import com.example.restaurant.service.RestaurantService;
import com.example.restaurant.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        // RestaurantRepository repository = new RestaurantMemoryRepository();
        RestaurantRepository repository = new RestaurantFileRepository();
        // RestaurantRepository repository = new RestaurantDBRepository();
        RestaurantService service = new RestaurantService(repository);
        ConsoleView view = new ConsoleView(service);
        view.run();
    }
}
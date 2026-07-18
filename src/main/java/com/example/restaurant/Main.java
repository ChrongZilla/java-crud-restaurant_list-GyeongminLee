package com.example.restaurant;

import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.RestaurantMemoryRepository;
import com.example.restaurant.repository.RestaurantFileRepository;
import com.example.restaurant.repository.RestaurantDBRepository;
import com.example.restaurant.service.RestaurantService;
import com.example.restaurant.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println("[메모리 버전으로 실행 중입니다]\n");
        RestaurantRepository repository = new RestaurantMemoryRepository();
         */
        /*
        System.out.println("[파일 버전으로 실행 중입니다]\n");
        RestaurantRepository repository = new RestaurantFileRepository();
         */

        System.out.println("[DB 버전으로 실행 중입니다]\n");
        RestaurantRepository repository = new RestaurantDBRepository();

        RestaurantService service = new RestaurantService(repository);
        ConsoleView view = new ConsoleView(service);
        view.run();
    }
}
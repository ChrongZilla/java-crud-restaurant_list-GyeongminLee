package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RestaurantFileRepository implements RestaurantRepository {

    private static final String FILE_PATH = "src/main/resources/restaurants.txt";
    private List<Restaurant> restaurants = new ArrayList<>();
    private Long nextId = 1L;

    public RestaurantFileRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;   // 파일 없으면 빈 상태로 시작

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            Long maxId = 0L;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split("\\|", -1);   // -1: 빈 필드도 유지
                Long id = Long.parseLong(parts[0]);
                String category = parts[1];
                String restaurantName = parts[2];
                String address = parts[3].isEmpty() ? null : parts[3];
                String phoneNumber = parts[4].isEmpty() ? null : parts[4];

                restaurants.add(new Restaurant(id, category, restaurantName, address, phoneNumber));
                if (id > maxId) maxId = id;
            }
            nextId = maxId + 1;

        } catch (IOException e) {
            System.out.println("파일 로드 실패: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {

            for (Restaurant r : restaurants) {
                String address = r.getAddress() == null ? "" : r.getAddress();
                String phoneNumber = r.getPhoneNumber() == null ? "" : r.getPhoneNumber();
                bw.write(r.getId() + "|" + r.getCategory() + "|" + r.getRestaurantName()
                        + "|" + address + "|" + phoneNumber);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("파일 저장 실패: " + e.getMessage());
        }
    }

    @Override
    public void save(Restaurant restaurant) {
        restaurant.setId(nextId++);
        restaurants.add(restaurant);
        saveToFile();
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        for (Restaurant r : restaurants)
            if (r.getId().equals(id))
                return r;
        return null;
    }

    @Override
    public boolean update(Restaurant restaurant) {
        Restaurant target = findById(restaurant.getId());
        if (target == null) return false;

        target.setCategory(restaurant.getCategory());
        target.setRestaurantName(restaurant.getRestaurantName());
        target.setAddress(restaurant.getAddress());
        target.setPhoneNumber(restaurant.getPhoneNumber());
        saveToFile();
        return true;
    }

    @Override
    public boolean delete(Long id) {
        boolean removed = restaurants.removeIf(r -> r.getId().equals(id));
        if (removed) saveToFile();
        return removed;
    }

    @Override
    public List<Restaurant> findByKeyword(String keyword) {
        List<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants)
            if (r.getRestaurantName().contains(keyword))
                result.add(r);
        return result;
    }

    @Override
    public List<Restaurant> findByCategory(String category) {
        List<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants)
            if (r.getCategory().equals(category))
                result.add(r);
        return result;
    }
}
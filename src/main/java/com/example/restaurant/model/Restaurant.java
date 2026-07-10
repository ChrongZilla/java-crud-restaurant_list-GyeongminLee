package com.example.restaurant.model;

public class Restaurant {
    // 멤버변수
    private Long id;            // id
    private String category;            // 업종명
    private String restaurantName;      // 식당명
    private String address;
    private String phoneNumber;         // 전화번호

    // 생성자
    public Restaurant() {}              // 기본 생성자
    // id 미포함 생성자
    public Restaurant(String category, String restaurantName, String address, String phoneNumber) {
        this.category = category;
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    // id 포함 생성자
    public Restaurant(Long id, String category, String restaurantName, String address, String phoneNumber) {
        this.id = id;
        this.category = category;
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // getter
    public Long getId() { return id; }
    public String getCategory() { return category; }
    public String getRestaurantName() { return restaurantName; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }

    // setter
    public void setId(Long id) { this.id = id; }
    public void setCategory(String category) { this.category = category; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}

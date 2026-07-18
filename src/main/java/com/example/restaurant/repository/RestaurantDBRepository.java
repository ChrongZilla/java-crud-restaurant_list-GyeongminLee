package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDBRepository implements RestaurantRepository {
    @Override
    public void save(Restaurant restaurant) {
        // id는 AUTO_INCREMENT
        String sql = "INSERT INTO restaurants (category, restaurant_name, address, phone_number) VALUES (?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, restaurant.getCategory());
            pstmt.setString(2, restaurant.getRestaurantName());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, restaurant.getPhoneNumber());

            pstmt.executeUpdate();   // INSERT라서 executeUpdate

        } catch (SQLException e) {
            System.out.println("등록 실패!! " + e.getMessage());
        }
    }

    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> result = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {   // SELECT라서 executeQuery

            while (rs.next()) {   // 결과 여러 건이라 while로 다 돌기
                Long id = rs.getLong("id");
                String category = rs.getString("category");
                String restaurantName = rs.getString("restaurant_name");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");

                result.add(new Restaurant(id, category, restaurantName, address, phoneNumber));
            }

        } catch (SQLException e) {
            System.out.println("조회 실패!! " + e.getMessage());
        }

        return result;
    }

    @Override
    public Restaurant findById(Long id) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);   // String 아니고 Long이라 setLong 사용

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {   // 결과 한 건뿐이라 while 대신 if
                    String category = rs.getString("category");
                    String restaurantName = rs.getString("restaurant_name");
                    String address = rs.getString("address");
                    String phoneNumber = rs.getString("phone_number");
                    return new Restaurant(id, category, restaurantName, address, phoneNumber);
                }
            }

        } catch (SQLException e) {
            System.out.println("조회 실패!! " + e.getMessage());
        }

        return null;   // 못 찾으면 null
    }

    @Override
    public boolean update(Restaurant restaurant) {
        String sql = "UPDATE restaurants SET category = ?, restaurant_name = ?, address = ?, phone_number = ? WHERE id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, restaurant.getCategory());
            pstmt.setString(2, restaurant.getRestaurantName());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, restaurant.getPhoneNumber());
            pstmt.setLong(5, restaurant.getId());   // WHERE 조건에 들어갈 id

            int affected = pstmt.executeUpdate();   // 실제로 몇 행이 바뀌었는지 반환됨
            return affected > 0;   // 1건 이상 바뀌었으면 성공

        } catch (SQLException e) {
            System.out.println("수정 실패!! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM restaurants WHERE id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            int affected = pstmt.executeUpdate();
            return affected > 0;   // 지워진 행이 있으면 성공

        } catch (SQLException e) {
            System.out.println("삭제 실패!! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Restaurant> findByKeyword(String keyword) {
        List<Restaurant> result = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE restaurant_name LIKE ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");   // 앞뒤 % : 부분 일치 검색

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String category = rs.getString("category");
                    String restaurantName = rs.getString("restaurant_name");
                    String address = rs.getString("address");
                    String phoneNumber = rs.getString("phone_number");
                    result.add(new Restaurant(id, category, restaurantName, address, phoneNumber));
                }
            }

        } catch (SQLException e) {
            System.out.println("검색 실패!! " + e.getMessage());
        }

        return result;
    }

    @Override
    public List<Restaurant> findByCategory(String f_category) {
        List<Restaurant> result = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE category = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, f_category);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String category = rs.getString("category");
                    String restaurantName = rs.getString("restaurant_name");
                    String address = rs.getString("address");
                    String phoneNumber = rs.getString("phone_number");
                    result.add(new Restaurant(id, category, restaurantName, address, phoneNumber));
                }
            }

        } catch (SQLException e) {
            System.out.println("검색 실패!! " + e.getMessage());
        }

        return result;
    }
}
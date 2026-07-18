package com.example.restaurant.seed;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantFileRepository;
import com.example.restaurant.repository.RestaurantDBRepository;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.service.RestaurantService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CsvSeeder {
    private static final String CSV_PATH = "src/main/resources/pohang_restaurants.csv";
    // 따옴표 안의 콤마는 무시하고 split하는 정규식
    private static final String CSV_SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    public static void main(String[] args) {
        // RestaurantRepository repository = new RestaurantFileRepository();
        RestaurantRepository repository = new RestaurantDBRepository();
        RestaurantService service = new RestaurantService(repository);

        int successCount = 0;
        int skipCount = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(CSV_PATH), Charset.forName("EUC-KR")))) {

            br.readLine();  // 헤더 줄 건너뛰기
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(CSV_SPLIT_REGEX, -1);
                if (parts.length < 5) continue;   // 컬럼 수 안 맞으면 건너뛰기

                String category = stripQuotes(parts[0]);
                String restaurantName = stripQuotes(parts[1]);
                String address = stripQuotes(parts[2]);
                String phoneNumber = stripQuotes(parts[4]).trim();

                // 포항시 북구만 필터링
                if (!address.contains("포항시 북구")) continue;

                // 도로명주소가 비어있는 행은 건너뛰기
                if (address.isBlank()) continue;

                if (phoneNumber.isBlank()) phoneNumber = null;

                Restaurant restaurant = new Restaurant(category, restaurantName, address, phoneNumber);
                try {
                    service.save(restaurant);
                    successCount++;
                } catch (IllegalArgumentException e) {
                    skipCount++;   // 검증 실패(빈 이름, 잘못된 category, 중복 등)는 건너뛰기
                }
            }

        } catch (IOException e) {
            System.out.println("CSV 읽기 실패: " + e.getMessage());
        }

        System.out.println("시딩 완료 - 성공: " + successCount + "건, 건너뜀: " + skipCount + "건");
    }

    // 필드 앞뒤의 큰따옴표 제거
    private static String stripQuotes(String field) {
        field = field.trim();
        if (field.startsWith("\"") && field.endsWith("\"") && field.length() >= 2) {
            field = field.substring(1, field.length() - 1);
        }
        return field;
    }
}
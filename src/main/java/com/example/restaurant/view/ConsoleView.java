package com.example.restaurant.view;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.service.RestaurantService;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final RestaurantService service;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleView(RestaurantService service) {
        this.service = service;
    }

    // 메인 루프
    public void run() {
        while (true) {
            printMenu();
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (choice) {
                case 1 -> register();          // 등록
                case 2 -> findAll();           // 전체 조회
                case 3 -> updateRestaurant();  // 수정
                case 4 -> deleteRestaurant();  // 삭제
                case 5 -> search();            // 검색
                case 0 -> { System.out.println("종료합니다."); return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== 포항 맛집 관리 ===");
        System.out.println("1. 등록  2. 전체조회  3. 수정  4. 삭제  5. 검색  0. 종료");
        System.out.print("선택 > ");
    }

    private void register() {
        // 필드 입력받아서 Restaurant 생성 후 service.save() 호출
        String category, restaurantName, address, phoneNumber;
        System.out.println("** 등록 **");
        System.out.print("업종명(일반음식점/휴게음식점/제과점영업) : ");
        category = sc.nextLine();
        System.out.print("업소명 : ");
        restaurantName = sc.nextLine();
        System.out.print("주소 : ");
        address = sc.nextLine();
        System.out.print("전화번호 : ");
        phoneNumber = sc.nextLine();

        Restaurant restaurant = new Restaurant(category, restaurantName, address, phoneNumber);
        try {
            service.save(restaurant);
            System.out.println("등록 완료!");
        } catch (IllegalArgumentException e) {
            System.out.println("\n등록 실패!! " + e.getMessage());
        }
    }

    private void findAll() {
        // service.findAll() 호출해서 결과 리스트 출력
        List<Restaurant> restaurants = service.findAll();

        if(restaurants.isEmpty()) {
            System.out.println("등록된 맛집이 없습니다.");
            return;
        }

        System.out.println("** 전체 조회 ** (총 " + restaurants.size() + "건)");

        int pageSize = 10;
        int total = restaurants.size();

        for (int i = 0; i < total; i++) {
            System.out.println(restaurants.get(i));

            boolean isPageEnd = (i + 1) % pageSize == 0;
            boolean isLast = (i == total - 1);

            if (isPageEnd && !isLast) {
                System.out.print("-- 더 보시겠습니까? (Enter: 계속, q: 그만) : ");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
    }

    private void updateRestaurant() {
        // id 입력받고, 새 값들 입력받아서 Restaurant 생성 후 service.update() 호출
        System.out.println("** 수정 **");
        System.out.print("수정할 id : ");
        Long id = Long.parseLong(sc.nextLine());
        /*
        Long id = sc.nextLong();
        sc.nextLine();
         */

        Restaurant existing = service.findById(id);
        if (existing == null) {
            System.out.println("해당 id의 맛집이 없습니다.");
            return;
        }

        System.out.println("기존 정보: " + existing);

        String category, restaurantName, address, phoneNumber;
        System.out.print("새 업종명 : ");
        category = sc.nextLine();
        System.out.print("새 업소명 : ");
        restaurantName = sc.nextLine();
        System.out.print("새 주소 : ");
        address = sc.nextLine();
        System.out.print("새 전화번호 : ");
        phoneNumber = sc.nextLine();

        Restaurant updated = new Restaurant(id, category, restaurantName, address, phoneNumber);
        try {
            boolean result = service.update(updated);
            System.out.println(result ? "수정 완료!" : "수정 실패.");
        } catch (IllegalArgumentException e) {
            System.out.println("\n수정 실패!! " + e.getMessage());
        }
    }

    private void deleteRestaurant() {
        // id 입력받아서 service.delete() 호출, 결과에 따라 메시지 출력
        System.out.println("** 삭제 **");
        System.out.print("삭제할 id : ");
        Long id = Long.parseLong(sc.nextLine());

        Restaurant existing = service.findById(id);
        if (existing == null) {
            System.out.println("해당 id의 맛집이 없습니다.");
            return;
        }

        System.out.println("삭제할 정보: " + existing);
        System.out.print("정말 삭제하시겠습니까? (y / n) : ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            boolean result = service.delete(id);
            System.out.println(result ? "삭제 완료!" : "삭제 실패.");
        } else {
            System.out.println("삭제 취소되었습니다.");
        }
    }

    private void search() {
        // 이름 검색 / 카테고리 검색 중 선택 → service.findByKeyword() 또는 findByCategory() 호출 후 출력
        System.out.println("** 검색 **");
        System.out.println("1. 이름 검색  2. 업종 검색");
        System.out.print("선택 > ");
        int type = Integer.parseInt(sc.nextLine());

        List<Restaurant> result;
        if (type == 1) {
            System.out.print("검색어(이름) : ");
            String keyword = sc.nextLine();
            result = service.findByKeyword(keyword);
        } else if (type == 2) {
            System.out.print("업종명(일반음식점/휴게음식점/제과점영업) : ");
            String category = sc.nextLine();
            result = service.findByCategory(category);
        } else {
            System.out.println("잘못된 입력입니다.");
            return;
        }

        if (result.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        System.out.println("검색 결과 (총 " + result.size() + "건)");
        for (Restaurant r : result) {
            System.out.println(r);
        }
    }
}
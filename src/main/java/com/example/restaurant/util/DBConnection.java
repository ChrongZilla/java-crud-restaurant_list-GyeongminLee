package com.example.restaurant.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    // Singleton: 자기 자신을 담는 정적 필드
    private static DBConnection instance;
    private Connection connection;

    // private 생성자 — 외부에서 new DBConnection() 못하게 막음
    private DBConnection() {
        try {
            // Properties 객체 만들기
            Properties props = new Properties();
            // getClass().getClassLoader().getResourceAsStream("db.properties")로 파일 읽어서 props.load()
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            props.load(input);

            // props.getProperty("db.url"), "db.username", "db.password", "db.driver") 꺼내기
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            // Class.forName(driver)로 드라이버 클래스 로드
            Class.forName(driver);

            // DriverManager.getConnection(url, username, password)로 connection 생성
            connection = DriverManager.getConnection(url, username, password);

            // 성공하면 "[DB 연결 성공]" 출력
            System.out.println("[DB 연결 성공]");

        } catch (Exception e) {
            System.out.println("DB 연결 실패!! " + e.getMessage());
        }
    }

    // 외부에서 인스턴스를 요청할 때 호출하는 메서드
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
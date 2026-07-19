package com.example.restaurant.util;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Properties;

public class VWorldGeocoder {

    private static String apiKey;

    static {
        try {
            Properties props = new Properties();
            InputStream input = VWorldGeocoder.class.getClassLoader().getResourceAsStream("vworld.properties");
            props.load(input);
            apiKey = props.getProperty("vworld.api.key");
        } catch (IOException e) {
            System.out.println("VWorld API 키 로드 실패!! " + e.getMessage());
        }
    }

    // 주소를 좌표(위도, 경도)로 변환. 실패하면 null 반환
    public static double[] getCoordinates(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://api.vworld.kr/req/address"
                    + "?service=address"
                    + "&request=getcoord"
                    + "&version=2.0"
                    + "&crs=epsg:4326"
                    + "&address=" + encodedAddress
                    + "&format=json"
                    + "&type=road"
                    + "&key=" + apiKey;

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            // System.out.println("HTTP 상태 코드: " + httpResponse.statusCode());   // 디버깅용
            // System.out.println("응답 본문: " + httpResponse.body());              // 디버깅용

            JSONObject json = new JSONObject(httpResponse.body());
            JSONObject response = json.getJSONObject("response");
            String status = response.getString("status");

            if (!status.equals("OK")) {
                return null;
            }

            JSONObject point = response.getJSONObject("result").getJSONObject("point");
            double x = point.getDouble("x");
            double y = point.getDouble("y");

            return new double[]{y, x};

        } catch (Exception e) {
            System.out.println("좌표 변환 실패!! " + e.getMessage());
            return null;
        }
    }

    // 하버사인 공식으로 두 좌표 사이 직선거리(km) 계산
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
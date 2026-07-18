CREATE TABLE restaurants (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             category VARCHAR(20) NOT NULL,
                             restaurant_name VARCHAR(100) NOT NULL,
                             address VARCHAR(255),
                             phone_number VARCHAR(20)
);
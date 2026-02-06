package com.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolManagementSystemApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementSystemApplication.class, args);
        System.out.println("========================================");
        System.out.println("School Management System is running!");
        System.out.println("API Base URL: http://localhost:8080/api");
        System.out.println("========================================");
    }
}

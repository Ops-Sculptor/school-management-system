package com.school.config;

import com.school.entity.*;
import com.school.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*import java.time.LocalDate;*

/**
 * DataInitializer - Initializes sample data for testing
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        // Check if data already exists
        if (userRepository.count() > 0) {
            log.info("Data already initialized. Skipping...");
            return;
        }
        
        log.info("Initializing sample data...");
        
        // Create Admin User
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("System Administrator");
        admin.setEmail("admin@school.com");
        admin.setPhone("1234567890");
        admin.setRole(User.Role.ADMIN);
        admin.setActive(true);
        userRepository.save(admin);
        log.info("Created admin user: username=admin, password=admin123");
            
        // Create Sample Classes
        SchoolClass class10A = new SchoolClass();
        class10A.setClassName("10-A");
        class10A.setGrade(10);
        class10A.setSection("A");
        class10A.setAcademicYear("2024-2025");
        class10A.setActive(true);
        schoolClassRepository.save(class10A);
        
        SchoolClass class9B = new SchoolClass();
        class9B.setClassName("9-B");
        class9B.setGrade(9);
        class9B.setSection("B");
        class9B.setAcademicYear("2024-2025");
        class9B.setActive(true);
        schoolClassRepository.save(class9B);
        
        log.info("Sample classes created");
        
        // Create Sample Teacher User
        User teacherUser = new User();
        teacherUser.setUsername("teacher1");
        teacherUser.setPassword(passwordEncoder.encode("teacher123"));
        teacherUser.setFullName("John Doe");
        teacherUser.setEmail("teacher@school.com");
        teacherUser.setPhone("9876543210");
        teacherUser.setRole(User.Role.TEACHER);
        teacherUser.setActive(true);
        userRepository.save(teacherUser);
        log.info("Created teacher user: username=teacher1, password=teacher123");
        
        // Create Sample Student User
        User studentUser = new User();
        studentUser.setUsername("student1");
        studentUser.setPassword(passwordEncoder.encode("student123"));
        studentUser.setFullName("Jane Smith");
        studentUser.setEmail("student@school.com");
        studentUser.setPhone("5555555555");
        studentUser.setRole(User.Role.STUDENT);
        studentUser.setActive(true);
        userRepository.save(studentUser);
        log.info("Created student user: username=student1, password=student123");
        
        log.info("Sample data initialization completed!");
        log.info("========================================");
        log.info("Login Credentials:");
        log.info("Admin    - username: admin     password: admin123");
        log.info("Teacher  - username: teacher1  password: teacher123");
        log.info("Student  - username: student1  password: student123");
        log.info("========================================");
    }
}

package com.school.repository;

import com.school.entity.SchoolClass;
import com.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentRepository - Data access layer for Student entity
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByRollNumber(String rollNumber);
    
    List<Student> findBySchoolClass(SchoolClass schoolClass);
    
    List<Student> findByActiveTrue();
    
    List<Student> findBySchoolClassAndActiveTrue(SchoolClass schoolClass);
    
    boolean existsByRollNumber(String rollNumber);
    
    Optional<Student> findByUserId(Long userId);
}

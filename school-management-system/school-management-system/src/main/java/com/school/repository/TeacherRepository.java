package com.school.repository;

import com.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * TeacherRepository - Data access layer for Teacher entity
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    Optional<Teacher> findByEmployeeId(String employeeId);
    
    List<Teacher> findByActiveTrue();
    
    boolean existsByEmployeeId(String employeeId);
    
    Optional<Teacher> findByUserId(Long userId);
}

package com.school.repository;

import com.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    Optional<Teacher> findByEmployeeId(String employeeId);
    
    Optional<Teacher> findByUserId(Long userId);
    
    List<Teacher> findByActive(Boolean active);
    
    Boolean existsByEmployeeId(String employeeId);
}

package com.school.repository;

import com.school.entity.Exam;
import com.school.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ExamRepository - Data access layer for Exam entity
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    
    List<Exam> findBySchoolClass(SchoolClass schoolClass);
    
    List<Exam> findByActiveTrue();
    
    List<Exam> findBySchoolClassAndActiveTrue(SchoolClass schoolClass);
}

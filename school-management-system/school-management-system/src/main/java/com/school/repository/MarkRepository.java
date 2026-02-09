package com.school.repository;

import com.school.entity.Exam;
import com.school.entity.Mark;
import com.school.entity.Student;
import com.school.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MarkRepository - Data access layer for Mark entity
 */
@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    
    List<Mark> findByStudent(Student student);
    
    List<Mark> findByExam(Exam exam);
    
    List<Mark> findByStudentAndExam(Student student, Exam exam);
    
    Optional<Mark> findByStudentAndExamAndSubject(Student student, Exam exam, Subject subject);
    
    List<Mark> findByExamAndSubject(Exam exam, Subject subject);
}

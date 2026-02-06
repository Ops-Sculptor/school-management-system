package com.school.repository;

import com.school.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    
    List<ExamResult> findByStudentId(Long studentId);
    
    List<ExamResult> findByExaminationId(Long examinationId);
    
    Optional<ExamResult> findByStudentIdAndExaminationId(Long studentId, Long examinationId);
    
    List<ExamResult> findByStatus(ExamResult.ResultStatus status);
}

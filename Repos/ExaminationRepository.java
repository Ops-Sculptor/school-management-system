package com.school.repository;

import com.school.entity.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    
    List<Examination> findBySchoolClassId(Long classId);
    
    List<Examination> findBySubjectId(Long subjectId);
    
    List<Examination> findByExamType(Examination.ExamType examType);
    
    List<Examination> findByActive(Boolean active);
}

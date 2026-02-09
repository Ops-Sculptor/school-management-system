package com.school.service;

import com.school.entity.Exam;
import com.school.entity.SchoolClass;
import com.school.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ExamService - Business logic for Exam operations
 */
@Service
@RequiredArgsConstructor
public class ExamService {
    
    private final ExamRepository examRepository;
    
    /**
     * Create a new exam
     */
    @Transactional
    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }
    
    /**
     * Get exam by ID
     */
    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }
    
    /**
     * Get all exams
     */
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
    
    /**
     * Get active exams
     */
    public List<Exam> getActiveExams() {
        return examRepository.findByActiveTrue();
    }
    
    /**
     * Get exams by class
     */
    public List<Exam> getExamsByClass(SchoolClass schoolClass) {
        return examRepository.findBySchoolClass(schoolClass);
    }
    
    /**
     * Update exam
     */
    @Transactional
    public Exam updateExam(Long id, Exam updatedExam) {
        Exam exam = getExamById(id);
        
        exam.setExamName(updatedExam.getExamName());
        exam.setStartDate(updatedExam.getStartDate());
        exam.setEndDate(updatedExam.getEndDate());
        exam.setAcademicYear(updatedExam.getAcademicYear());
        
        return examRepository.save(exam);
    }
    
    /**
     * Delete exam
     */
    @Transactional
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}

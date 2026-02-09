package com.school.service;

import com.school.entity.Exam;
import com.school.entity.Mark;
import com.school.entity.Student;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * MarkService - Business logic for Mark operations
 */
@Service
@RequiredArgsConstructor
public class MarkService {
    
    private final MarkRepository markRepository;
    
    /**
     * Enter marks
     */
    @Transactional
    public Mark enterMarks(Student student, Exam exam, Subject subject, Integer marks, Teacher enteredBy) {
        // Check if marks already exist
        Optional<Mark> existing = markRepository.findByStudentAndExamAndSubject(student, exam, subject);
        
        if (existing.isPresent()) {
            // Update existing marks
            Mark mark = existing.get();
            mark.setMarksObtained(marks);
            mark.setEnteredBy(enteredBy);
            mark.calculateGrade();
            return markRepository.save(mark);
        } else {
            // Create new mark record
            Mark mark = new Mark();
            mark.setStudent(student);
            mark.setExam(exam);
            mark.setSubject(subject);
            mark.setMarksObtained(marks);
            mark.setMaxMarks(subject.getMaxMarks());
            mark.setEnteredBy(enteredBy);
            mark.calculateGrade();
            return markRepository.save(mark);
        }
    }
    
    /**
     * Get mark by ID
     */
    public Mark getMarkById(Long id) {
        return markRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mark not found with id: " + id));
    }
    
    /**
     * Get marks by student
     */
    public List<Mark> getMarksByStudent(Student student) {
        return markRepository.findByStudent(student);
    }
    
    /**
     * Get marks by exam
     */
    public List<Mark> getMarksByExam(Exam exam) {
        return markRepository.findByExam(exam);
    }
    
    /**
     * Get marks by student and exam
     */
    public List<Mark> getMarksByStudentAndExam(Student student, Exam exam) {
        return markRepository.findByStudentAndExam(student, exam);
    }
    
    /**
     * Get marks by exam and subject
     */
    public List<Mark> getMarksByExamAndSubject(Exam exam, Subject subject) {
        return markRepository.findByExamAndSubject(exam, subject);
    }
    
    /**
     * Calculate total marks for a student in an exam
     */
    public Integer calculateTotalMarks(Student student, Exam exam) {
        List<Mark> marks = getMarksByStudentAndExam(student, exam);
        return marks.stream()
                .mapToInt(Mark::getMarksObtained)
                .sum();
    }
    
    /**
     * Calculate percentage for a student in an exam
     */
    public Double calculatePercentage(Student student, Exam exam) {
        List<Mark> marks = getMarksByStudentAndExam(student, exam);
        
        if (marks.isEmpty()) {
            return 0.0;
        }
        
        int totalMarksObtained = marks.stream().mapToInt(Mark::getMarksObtained).sum();
        int totalMaxMarks = marks.stream().mapToInt(Mark::getMaxMarks).sum();
        
        if (totalMaxMarks == 0) {
            return 0.0;
        }
        
        return (totalMarksObtained * 100.0) / totalMaxMarks;
    }
    
    /**
     * Update marks
     */
    @Transactional
    public Mark updateMarks(Long id, Integer marks, String remarks) {
        Mark mark = getMarkById(id);
        mark.setMarksObtained(marks);
        mark.setRemarks(remarks);
        mark.calculateGrade();
        return markRepository.save(mark);
    }
    
    /**
     * Delete marks
     */
    @Transactional
    public void deleteMarks(Long id) {
        markRepository.deleteById(id);
    }
}

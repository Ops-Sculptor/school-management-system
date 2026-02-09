package com.school.service;

import com.school.entity.SchoolClass;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SubjectService - Business logic for Subject operations
 */
@Service
@RequiredArgsConstructor
public class SubjectService {
    
    private final SubjectRepository subjectRepository;
    
    /**
     * Create a new subject
     */
    @Transactional
    public Subject createSubject(Subject subject) {
        // Check if subject code already exists
        if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
            throw new RuntimeException("Subject code already exists: " + subject.getSubjectCode());
        }
        
        return subjectRepository.save(subject);
    }
    
    /**
     * Get subject by ID
     */
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }
    
    /**
     * Get subject by code
     */
    public Subject getSubjectByCode(String subjectCode) {
        return subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new RuntimeException("Subject not found with code: " + subjectCode));
    }
    
    /**
     * Get all subjects
     */
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    
    /**
     * Get active subjects
     */
    public List<Subject> getActiveSubjects() {
        return subjectRepository.findByActiveTrue();
    }
    
    /**
     * Get subjects by class
     */
    public List<Subject> getSubjectsByClass(SchoolClass schoolClass) {
        return subjectRepository.findBySchoolClass(schoolClass);
    }
    
    /**
     * Get subjects by teacher
     */
    public List<Subject> getSubjectsByTeacher(Teacher teacher) {
        return subjectRepository.findByTeachersContaining(teacher);
    }
    
    /**
     * Assign teacher to subject
     */
    @Transactional
    public Subject assignTeacher(Long subjectId, Teacher teacher) {
        Subject subject = getSubjectById(subjectId);
        subject.getTeachers().add(teacher);
        return subjectRepository.save(subject);
    }
    
    /**
     * Remove teacher from subject
     */
    @Transactional
    public Subject removeTeacher(Long subjectId, Teacher teacher) {
        Subject subject = getSubjectById(subjectId);
        subject.getTeachers().remove(teacher);
        return subjectRepository.save(subject);
    }
    
    /**
     * Update subject
     */
    @Transactional
    public Subject updateSubject(Long id, Subject updatedSubject) {
        Subject subject = getSubjectById(id);
        
        subject.setSubjectName(updatedSubject.getSubjectName());
        subject.setMaxMarks(updatedSubject.getMaxMarks());
        subject.setPassingMarks(updatedSubject.getPassingMarks());
        
        return subjectRepository.save(subject);
    }
    
    /**
     * Delete subject
     */
    @Transactional
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}

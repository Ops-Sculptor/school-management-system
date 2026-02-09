package com.school.service;

import com.school.entity.SchoolClass;
import com.school.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SchoolClassService - Business logic for SchoolClass operations
 */
@Service
@RequiredArgsConstructor
public class SchoolClassService {
    
    private final SchoolClassRepository schoolClassRepository;
    
    /**
     * Create a new class
     */
    @Transactional
    public SchoolClass createClass(SchoolClass schoolClass) {
        // Check if class name already exists
        if (schoolClassRepository.existsByClassName(schoolClass.getClassName())) {
            throw new RuntimeException("Class name already exists: " + schoolClass.getClassName());
        }
        
        return schoolClassRepository.save(schoolClass);
    }
    
    /**
     * Get class by ID
     */
    public SchoolClass getClassById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
    }
    
    /**
     * Get class by name
     */
    public SchoolClass getClassByName(String className) {
        return schoolClassRepository.findByClassName(className)
                .orElseThrow(() -> new RuntimeException("Class not found with name: " + className));
    }
    
    /**
     * Get all classes
     */
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }
    
    /**
     * Get active classes
     */
    public List<SchoolClass> getActiveClasses() {
        return schoolClassRepository.findByActiveTrue();
    }
    
    /**
     * Get classes by grade
     */
    public List<SchoolClass> getClassesByGrade(Integer grade) {
        return schoolClassRepository.findByGrade(grade);
    }
    
    /**
     * Update class
     */
    @Transactional
    public SchoolClass updateClass(Long id, SchoolClass updatedClass) {
        SchoolClass schoolClass = getClassById(id);
        
        schoolClass.setClassName(updatedClass.getClassName());
        schoolClass.setGrade(updatedClass.getGrade());
        schoolClass.setSection(updatedClass.getSection());
        schoolClass.setAcademicYear(updatedClass.getAcademicYear());
        
        return schoolClassRepository.save(schoolClass);
    }
    
    /**
     * Deactivate class
     */
    @Transactional
    public void deactivateClass(Long id) {
        SchoolClass schoolClass = getClassById(id);
        schoolClass.setActive(false);
        schoolClassRepository.save(schoolClass);
    }
    
    /**
     * Delete class
     */
    @Transactional
    public void deleteClass(Long id) {
        schoolClassRepository.deleteById(id);
    }
}

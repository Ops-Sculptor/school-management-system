package com.school.service;

import com.school.entity.Teacher;
import com.school.entity.User;
import com.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TeacherService - Business logic for Teacher operations
 */
@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    private final UserService userService;
    
    /**
     * Create a new teacher
     */
    @Transactional
    public Teacher createTeacher(Teacher teacher, String password) {
        // Check if employee ID already exists
        if (teacherRepository.existsByEmployeeId(teacher.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists: " + teacher.getEmployeeId());
        }
        
        // Create user account for teacher
        User user = new User();
        user.setUsername(teacher.getEmployeeId());
        user.setPassword(password);
        user.setFullName(teacher.getFirstName() + " " + teacher.getLastName());
        user.setEmail(teacher.getUser().getEmail());
        user.setPhone(teacher.getUser().getPhone());
        user.setRole(User.Role.TEACHER);
        user.setActive(true);
        
        User savedUser = userService.createUser(user);
        teacher.setUser(savedUser);
        
        return teacherRepository.save(teacher);
    }
    
    /**
     * Get teacher by ID
     */
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }
    
    /**
     * Get teacher by employee ID
     */
    public Teacher getTeacherByEmployeeId(String employeeId) {
        return teacherRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with employee ID: " + employeeId));
    }
    
    /**
     * Get teacher by user ID
     */
    public Teacher getTeacherByUserId(Long userId) {
        return teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for user id: " + userId));
    }
    
    /**
     * Get all teachers
     */
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    
    /**
     * Get active teachers
     */
    public List<Teacher> getActiveTeachers() {
        return teacherRepository.findByActiveTrue();
    }
    
    /**
     * Update teacher
     */
    @Transactional
    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher teacher = getTeacherById(id);
        
        teacher.setFirstName(updatedTeacher.getFirstName());
        teacher.setLastName(updatedTeacher.getLastName());
        teacher.setDateOfBirth(updatedTeacher.getDateOfBirth());
        teacher.setGender(updatedTeacher.getGender());
        teacher.setQualification(updatedTeacher.getQualification());
        teacher.setSpecialization(updatedTeacher.getSpecialization());
        teacher.setAddress(updatedTeacher.getAddress());
        
        return teacherRepository.save(teacher);
    }
    
    /**
     * Deactivate teacher
     */
    @Transactional
    public void deactivateTeacher(Long id) {
        Teacher teacher = getTeacherById(id);
        teacher.setActive(false);
        teacherRepository.save(teacher);
        
        // Also deactivate user account
        userService.deactivateUser(teacher.getUser().getId());
    }
    
    /**
     * Delete teacher
     */
    @Transactional
    public void deleteTeacher(Long id) {
        Teacher teacher = getTeacherById(id);
        Long userId = teacher.getUser().getId();
        
        teacherRepository.deleteById(id);
        userService.deleteUser(userId);
    }
}

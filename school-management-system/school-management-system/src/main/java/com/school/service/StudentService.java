package com.school.service;

import com.school.entity.SchoolClass;
import com.school.entity.Student;
import com.school.entity.User;
import com.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * StudentService - Business logic for Student operations
 */
@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final UserService userService;
    
    /**
     * Create a new student
     */
    @Transactional
    public Student createStudent(Student student, String password) {
        // Check if roll number already exists
        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            throw new RuntimeException("Roll number already exists: " + student.getRollNumber());
        }
        
        // Create user account for student
        User user = new User();
        user.setUsername(student.getRollNumber());
        user.setPassword(password);
        user.setFullName(student.getFirstName() + " " + student.getLastName());
        user.setEmail(student.getUser().getEmail());
        user.setPhone(student.getUser().getPhone());
        user.setRole(User.Role.STUDENT);
        user.setActive(true);
        
        User savedUser = userService.createUser(user);
        student.setUser(savedUser);
        
        return studentRepository.save(student);
    }
    
    /**
     * Get student by ID
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
    
    /**
     * Get student by roll number
     */
    public Student getStudentByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with roll number: " + rollNumber));
    }
    
    /**
     * Get student by user ID
     */
    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found for user id: " + userId));
    }
    
    /**
     * Get all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    /**
     * Get active students
     */
    public List<Student> getActiveStudents() {
        return studentRepository.findByActiveTrue();
    }
    
    /**
     * Get students by class
     */
    public List<Student> getStudentsByClass(SchoolClass schoolClass) {
        return studentRepository.findBySchoolClass(schoolClass);
    }
    
    /**
     * Get active students by class
     */
    public List<Student> getActiveStudentsByClass(SchoolClass schoolClass) {
        return studentRepository.findBySchoolClassAndActiveTrue(schoolClass);
    }
    
    /**
     * Update student
     */
    @Transactional
    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = getStudentById(id);
        
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setDateOfBirth(updatedStudent.getDateOfBirth());
        student.setGender(updatedStudent.getGender());
        student.setAddress(updatedStudent.getAddress());
        student.setFatherName(updatedStudent.getFatherName());
        student.setMotherName(updatedStudent.getMotherName());
        student.setParentPhone(updatedStudent.getParentPhone());
        student.setSchoolClass(updatedStudent.getSchoolClass());
        
        return studentRepository.save(student);
    }
    
    /**
     * Deactivate student
     */
    @Transactional
    public void deactivateStudent(Long id) {
        Student student = getStudentById(id);
        student.setActive(false);
        studentRepository.save(student);
        
        // Also deactivate user account
        userService.deactivateUser(student.getUser().getId());
    }
    
    /**
     * Delete student
     */
    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        Long userId = student.getUser().getId();
        
        studentRepository.deleteById(id);
        userService.deleteUser(userId);
    }
}

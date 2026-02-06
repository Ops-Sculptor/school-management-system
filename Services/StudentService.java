package com.school.service;

import com.school.dto.StudentDTO;
import com.school.entity.SchoolClass;
import com.school.entity.Student;
import com.school.entity.User;
import com.school.repository.SchoolClassRepository;
import com.school.repository.StudentRepository;
import com.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository classRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public StudentDTO createStudent(StudentDTO dto) {
        // Check if username or roll number already exists
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (studentRepository.existsByRollNumber(dto.getRollNumber())) {
            throw new RuntimeException("Roll number already exists");
        }
        
        // Create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFirstName() + " " + dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(User.Role.STUDENT);
        user.setActive(true);
        user = userRepository.save(user);
        
        // Create student
        Student student = new Student();
        student.setUser(user);
        student.setRollNumber(dto.getRollNumber());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(Student.Gender.valueOf(dto.getGender().toUpperCase()));
        student.setAddress(dto.getAddress());
        student.setCity(dto.getCity());
        student.setState(dto.getState());
        student.setPinCode(dto.getPinCode());
        student.setBloodGroup(dto.getBloodGroup());
        student.setAdmissionDate(dto.getAdmissionDate());
        
        if (dto.getClassId() != null) {
            SchoolClass schoolClass = classRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            student.setSchoolClass(schoolClass);
        }
        
        student.setActive(true);
        student = studentRepository.save(student);
        
        return convertToDTO(student);
    }
    
    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(Student.Gender.valueOf(dto.getGender().toUpperCase()));
        student.setAddress(dto.getAddress());
        student.setCity(dto.getCity());
        student.setState(dto.getState());
        student.setPinCode(dto.getPinCode());
        student.setBloodGroup(dto.getBloodGroup());
        
        if (dto.getClassId() != null) {
            SchoolClass schoolClass = classRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            student.setSchoolClass(schoolClass);
        }
        
        student = studentRepository.save(student);
        return convertToDTO(student);
    }
    
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return convertToDTO(student);
    }
    
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<StudentDTO> getStudentsByClass(Long classId) {
        return studentRepository.findBySchoolClassId(classId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setActive(false);
        student.getUser().setActive(false);
        studentRepository.save(student);
    }
    
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setRollNumber(student.getRollNumber());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGender(student.getGender().name());
        dto.setAddress(student.getAddress());
        dto.setCity(student.getCity());
        dto.setState(student.getState());
        dto.setPinCode(student.getPinCode());
        dto.setBloodGroup(student.getBloodGroup());
        dto.setAdmissionDate(student.getAdmissionDate());
        dto.setUsername(student.getUser().getUsername());
        dto.setEmail(student.getUser().getEmail());
        dto.setPhoneNumber(student.getUser().getPhoneNumber());
        dto.setActive(student.getActive());
        
        if (student.getSchoolClass() != null) {
            dto.setClassId(student.getSchoolClass().getId());
            dto.setClassName(student.getSchoolClass().getClassName());
        }
        
        return dto;
    }
}

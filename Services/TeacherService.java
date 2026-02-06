package com.school.service;

import com.school.dto.TeacherDTO;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.entity.User;
import com.school.repository.SubjectRepository;
import com.school.repository.TeacherRepository;
import com.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public TeacherDTO createTeacher(TeacherDTO dto) {
        // Check if username or employee ID already exists
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (teacherRepository.existsByEmployeeId(dto.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists");
        }
        
        // Create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFirstName() + " " + dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(User.Role.TEACHER);
        user.setActive(true);
        user = userRepository.save(user);
        
        // Create teacher
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setEmployeeId(dto.getEmployeeId());
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setDateOfBirth(dto.getDateOfBirth());
        teacher.setGender(Teacher.Gender.valueOf(dto.getGender().toUpperCase()));
        teacher.setQualification(dto.getQualification());
        teacher.setSpecialization(dto.getSpecialization());
        teacher.setAddress(dto.getAddress());
        teacher.setCity(dto.getCity());
        teacher.setState(dto.getState());
        teacher.setPinCode(dto.getPinCode());
        teacher.setJoiningDate(dto.getJoiningDate());
        teacher.setSalary(dto.getSalary());
        teacher.setActive(true);
        
        teacher = teacherRepository.save(teacher);
        
        // Assign subjects
        if (dto.getSubjectIds() != null && !dto.getSubjectIds().isEmpty()) {
            assignSubjectsToTeacher(teacher.getId(), dto.getSubjectIds());
        }
        
        return convertToDTO(teacher);
    }
    
    @Transactional
    public TeacherDTO updateTeacher(Long id, TeacherDTO dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setDateOfBirth(dto.getDateOfBirth());
        teacher.setGender(Teacher.Gender.valueOf(dto.getGender().toUpperCase()));
        teacher.setQualification(dto.getQualification());
        teacher.setSpecialization(dto.getSpecialization());
        teacher.setAddress(dto.getAddress());
        teacher.setCity(dto.getCity());
        teacher.setState(dto.getState());
        teacher.setPinCode(dto.getPinCode());
        teacher.setSalary(dto.getSalary());
        
        teacher = teacherRepository.save(teacher);
        return convertToDTO(teacher);
    }
    
    @Transactional
    public void assignSubjectsToTeacher(Long teacherId, Set<Long> subjectIds) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        Set<Subject> subjects = new HashSet<>();
        for (Long subjectId : subjectIds) {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Subject not found: " + subjectId));
            subjects.add(subject);
        }
        
        teacher.setSubjects(subjects);
        teacherRepository.save(teacher);
    }
    
    public TeacherDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return convertToDTO(teacher);
    }
    
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacher.setActive(false);
        teacher.getUser().setActive(false);
        teacherRepository.save(teacher);
    }
    
    private TeacherDTO convertToDTO(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setEmployeeId(teacher.getEmployeeId());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setDateOfBirth(teacher.getDateOfBirth());
        dto.setGender(teacher.getGender().name());
        dto.setQualification(teacher.getQualification());
        dto.setSpecialization(teacher.getSpecialization());
        dto.setAddress(teacher.getAddress());
        dto.setCity(teacher.getCity());
        dto.setState(teacher.getState());
        dto.setPinCode(teacher.getPinCode());
        dto.setJoiningDate(teacher.getJoiningDate());
        dto.setSalary(teacher.getSalary());
        dto.setUsername(teacher.getUser().getUsername());
        dto.setEmail(teacher.getUser().getEmail());
        dto.setPhoneNumber(teacher.getUser().getPhoneNumber());
        dto.setActive(teacher.getActive());
        
        if (teacher.getSubjects() != null && !teacher.getSubjects().isEmpty()) {
            dto.setSubjectIds(teacher.getSubjects().stream()
                    .map(Subject::getId)
                    .collect(Collectors.toSet()));
        }
        
        return dto;
    }
}

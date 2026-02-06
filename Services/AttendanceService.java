package com.school.service;

import com.school.dto.AttendanceDTO;
import com.school.entity.Attendance;
import com.school.entity.Student;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.repository.AttendanceRepository;
import com.school.repository.StudentRepository;
import com.school.repository.SubjectRepository;
import com.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    
    @Transactional
    public AttendanceDTO markAttendance(AttendanceDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        // Check if attendance already exists for this date
        Attendance attendance = attendanceRepository
                .findByStudentIdAndSubjectIdAndDate(dto.getStudentId(), dto.getSubjectId(), dto.getDate())
                .orElse(new Attendance());
        
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setTeacher(teacher);
        attendance.setDate(dto.getDate());
        attendance.setStatus(Attendance.AttendanceStatus.valueOf(dto.getStatus().toUpperCase()));
        attendance.setRemarks(dto.getRemarks());
        
        attendance = attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }
    
    @Transactional
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));
        
        attendance.setStatus(Attendance.AttendanceStatus.valueOf(dto.getStatus().toUpperCase()));
        attendance.setRemarks(dto.getRemarks());
        
        attendance = attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }
    
    public List<AttendanceDTO> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getAttendanceByStudentAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByStudentIdAndDateRange(studentId, startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getAttendanceBySubjectAndDate(Long subjectId, LocalDate date) {
        return attendanceRepository.findBySubjectIdAndDate(subjectId, date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setStudentId(attendance.getStudent().getId());
        dto.setStudentName(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName());
        dto.setRollNumber(attendance.getStudent().getRollNumber());
        dto.setSubjectId(attendance.getSubject().getId());
        dto.setSubjectName(attendance.getSubject().getSubjectName());
        dto.setTeacherId(attendance.getTeacher().getId());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus().name());
        dto.setRemarks(attendance.getRemarks());
        return dto;
    }
}

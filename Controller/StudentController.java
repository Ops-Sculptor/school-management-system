package com.school.controller;

import com.school.dto.AttendanceDTO;
import com.school.dto.ExamResultDTO;
import com.school.dto.StudentDTO;
import com.school.service.AttendanceService;
import com.school.service.ExamResultService;
import com.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
public class StudentController {
    
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final ExamResultService examResultService;
    
    // View Profile
    @GetMapping("/profile/{id}")
    public ResponseEntity<StudentDTO> getProfile(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    // View Attendance
    @GetMapping("/attendance/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendance(@PathVariable Long studentId) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceByStudent(studentId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/attendance/{studentId}/range")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceByStudentAndDateRange(
                studentId, startDate, endDate);
        return ResponseEntity.ok(attendance);
    }
    
    // View Marks
    @GetMapping("/marks/{studentId}")
    public ResponseEntity<List<ExamResultDTO>> getMarks(@PathVariable Long studentId) {
        List<ExamResultDTO> results = examResultService.getResultsByStudent(studentId);
        return ResponseEntity.ok(results);
    }
}

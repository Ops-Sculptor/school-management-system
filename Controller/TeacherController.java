package com.school.controller;

import com.school.dto.AttendanceDTO;
import com.school.dto.ExamResultDTO;
import com.school.service.AttendanceService;
import com.school.service.ExamResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
public class TeacherController {
    
    private final AttendanceService attendanceService;
    private final ExamResultService examResultService;
    
    // Attendance Management
    @PostMapping("/attendance")
    public ResponseEntity<AttendanceDTO> markAttendance(@Valid @RequestBody AttendanceDTO dto) {
        AttendanceDTO attendance = attendanceService.markAttendance(dto);
        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }
    
    @PutMapping("/attendance/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Long id, 
                                                           @Valid @RequestBody AttendanceDTO dto) {
        AttendanceDTO attendance = attendanceService.updateAttendance(id, dto);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/attendance/subject/{subjectId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceBySubjectAndDate(
            @PathVariable Long subjectId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceBySubjectAndDate(subjectId, date);
        return ResponseEntity.ok(attendance);
    }
    
    // Marks Management
    @PostMapping("/marks")
    public ResponseEntity<ExamResultDTO> enterMarks(@Valid @RequestBody ExamResultDTO dto,
                                                      Authentication authentication) {
        // In real application, extract teacher ID from authenticated user
        Long teacherId = 1L; // Placeholder
        ExamResultDTO result = examResultService.enterMarks(dto, teacherId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @PutMapping("/marks/{id}")
    public ResponseEntity<ExamResultDTO> updateMarks(@PathVariable Long id, 
                                                      @Valid @RequestBody ExamResultDTO dto) {
        ExamResultDTO result = examResultService.updateMarks(id, dto);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/marks/examination/{examinationId}")
    public ResponseEntity<List<ExamResultDTO>> getResultsByExamination(@PathVariable Long examinationId) {
        List<ExamResultDTO> results = examResultService.getResultsByExamination(examinationId);
        return ResponseEntity.ok(results);
    }
}

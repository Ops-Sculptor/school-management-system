package com.school.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    private String studentName;
    private String rollNumber;
    
    @NotNull(message = "Subject ID is required")
    private Long subjectId;
    
    private String subjectName;
    
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Attendance status is required")
    private String status;
    
    private String remarks;
}

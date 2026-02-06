package com.school.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultDTO {
    
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    private String studentName;
    private String rollNumber;
    
    @NotNull(message = "Examination ID is required")
    private Long examinationId;
    
    private String examName;
    private String subjectName;
    private Integer maxMarks;
    
    @NotNull(message = "Marks obtained is required")
    private Double marksObtained;
    
    private String grade;
    private String status;
    private String remarks;
    private String enteredByTeacher;
}

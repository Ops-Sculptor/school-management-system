package com.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam_results", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "examination_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;
    
    @Column(nullable = false)
    private Double marksObtained;
    
    @Column(nullable = false)
    private String grade;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus status;
    
    private String remarks;
    
    @ManyToOne
    @JoinColumn(name = "entered_by")
    private Teacher enteredBy;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum ResultStatus {
        PASS,
        FAIL,
        ABSENT
    }
}

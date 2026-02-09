package com.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Mark Entity - Stores student marks for subjects
 */
@Entity
@Table(name = "marks",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "exam_id", "subject_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;
    
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @Column(nullable = false)
    private Integer marksObtained;
    
    private Integer maxMarks = 100;
    
    private String grade; // A+, A, B+, B, C, F
    
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
    
    // Calculate grade based on percentage
    public void calculateGrade() {
        double percentage = (marksObtained * 100.0) / maxMarks;
        if (percentage >= 90) grade = "A+";
        else if (percentage >= 80) grade = "A";
        else if (percentage >= 70) grade = "B+";
        else if (percentage >= 60) grade = "B";
        else if (percentage >= 50) grade = "C";
        else if (percentage >= 40) grade = "D";
        else grade = "F";
    }
}

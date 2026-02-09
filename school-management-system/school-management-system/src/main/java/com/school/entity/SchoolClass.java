package com.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * SchoolClass Entity - Represents a class (e.g., Class 10-A)
 */
@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String className; // e.g., "10-A", "9-B"
    
    @Column(nullable = false)
    private Integer grade; // e.g., 10, 9, 8
    
    @Column(nullable = false)
    private String section; // e.g., "A", "B", "C"
    
    private String academicYear; // e.g., "2024-2025"
    
    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students = new HashSet<>();
    
    @OneToMany(mappedBy = "schoolClass")
    private Set<Subject> subjects = new HashSet<>();
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

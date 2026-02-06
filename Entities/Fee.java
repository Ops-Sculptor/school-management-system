package com.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeeType feeType;
    
    @Column(nullable = false)
    private Double totalAmount;
    
    @Column(nullable = false)
    private Double paidAmount;
    
    @Column(nullable = false)
    private Double balance;
    
    @Column(nullable = false)
    private LocalDate dueDate;
    
    private LocalDate paidDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    private String transactionId;
    
    private String remarks;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum FeeType {
        TUITION,
        ADMISSION,
        EXAM,
        LIBRARY,
        SPORTS,
        TRANSPORT,
        OTHER
    }
    
    public enum PaymentStatus {
        PENDING,
        PARTIAL,
        PAID,
        OVERDUE
    }
    
    public enum PaymentMethod {
        CASH,
        CARD,
        ONLINE,
        CHEQUE
    }
}

package com.school.repository;

import com.school.entity.Fee;
import com.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FeeRepository - Data access layer for Fee entity
 */
@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {
    
    List<Fee> findByStudent(Student student);
    
    List<Fee> findByPaymentStatus(Fee.PaymentStatus status);
    
    List<Fee> findByStudentAndPaymentStatus(Student student, Fee.PaymentStatus status);
}

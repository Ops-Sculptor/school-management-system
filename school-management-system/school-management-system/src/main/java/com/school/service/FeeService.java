package com.school.service;

import com.school.entity.Fee;
import com.school.entity.Student;
import com.school.repository.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * FeeService - Business logic for Fee operations
 */
@Service
@RequiredArgsConstructor
public class FeeService {
    
    private final FeeRepository feeRepository;
    
    /**
     * Create fee record
     */
    @Transactional
    public Fee createFee(Fee fee) {
        return feeRepository.save(fee);
    }
    
    /**
     * Get fee by ID
     */
    public Fee getFeeById(Long id) {
        return feeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fee not found with id: " + id));
    }
    
    /**
     * Get all fees
     */
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }
    
    /**
     * Get fees by student
     */
    public List<Fee> getFeesByStudent(Student student) {
        return feeRepository.findByStudent(student);
    }
    
    /**
     * Get fees by payment status
     */
    public List<Fee> getFeesByStatus(Fee.PaymentStatus status) {
        return feeRepository.findByPaymentStatus(status);
    }
    
    /**
     * Get pending fees for a student
     */
    public List<Fee> getPendingFees(Student student) {
        return feeRepository.findByStudentAndPaymentStatus(student, Fee.PaymentStatus.PENDING);
    }
    
    /**
     * Record payment
     */
    @Transactional
    public Fee recordPayment(Long feeId, BigDecimal paidAmount, String paymentMode, String transactionId) {
        Fee fee = getFeeById(feeId);
        
        fee.setPaidAmount(fee.getPaidAmount().add(paidAmount));
        fee.setPaymentDate(LocalDate.now());
        fee.setPaymentMode(paymentMode);
        fee.setTransactionId(transactionId);
        
        // Update payment status
        if (fee.getPaidAmount().compareTo(fee.getAmount()) >= 0) {
            fee.setPaymentStatus(Fee.PaymentStatus.PAID);
        } else if (fee.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            fee.setPaymentStatus(Fee.PaymentStatus.PARTIAL);
        }
        
        return feeRepository.save(fee);
    }
    
    /**
     * Update fee
     */
    @Transactional
    public Fee updateFee(Long id, Fee updatedFee) {
        Fee fee = getFeeById(id);
        
        fee.setFeeType(updatedFee.getFeeType());
        fee.setAmount(updatedFee.getAmount());
        fee.setDueDate(updatedFee.getDueDate());
        
        return feeRepository.save(fee);
    }
    
    /**
     * Delete fee
     */
    @Transactional
    public void deleteFee(Long id) {
        feeRepository.deleteById(id);
    }
    
    /**
     * Calculate total pending amount for a student
     */
    public BigDecimal calculateTotalPendingAmount(Student student) {
        List<Fee> pendingFees = getPendingFees(student);
        return pendingFees.stream()
                .map(Fee::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

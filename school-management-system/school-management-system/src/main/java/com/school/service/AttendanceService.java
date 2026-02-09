package com.school.service;

import com.school.entity.Attendance;
import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * AttendanceService - Business logic for Attendance operations
 */
@Service
@RequiredArgsConstructor
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    /**
     * Mark attendance
     */
    @Transactional
    public Attendance markAttendance(Student student, LocalDate date, Attendance.Status status, Teacher markedBy) {
        // Check if attendance already exists for this date
        Optional<Attendance> existing = attendanceRepository.findByStudentAndAttendanceDate(student, date);
        
        if (existing.isPresent()) {
            // Update existing attendance
            Attendance attendance = existing.get();
            attendance.setStatus(status);
            attendance.setMarkedBy(markedBy);
            return attendanceRepository.save(attendance);
        } else {
            // Create new attendance record
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setAttendanceDate(date);
            attendance.setStatus(status);
            attendance.setMarkedBy(markedBy);
            return attendanceRepository.save(attendance);
        }
    }
    
    /**
     * Get attendance by ID
     */
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }
    
    /**
     * Get attendance by student
     */
    public List<Attendance> getAttendanceByStudent(Student student) {
        return attendanceRepository.findByStudent(student);
    }
    
    /**
     * Get attendance by date
     */
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }
    
    /**
     * Get attendance by student and date range
     */
    public List<Attendance> getAttendanceByStudentAndDateRange(Student student, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByStudentAndAttendanceDateBetween(student, startDate, endDate);
    }
    
    /**
     * Calculate attendance percentage
     */
    public Double calculateAttendancePercentage(Student student) {
        Long totalDays = attendanceRepository.countTotalDays(student);
        if (totalDays == 0) {
            return 0.0;
        }
        
        Long presentDays = attendanceRepository.countPresentDays(student);
        return (presentDays * 100.0) / totalDays;
    }
    
    /**
     * Update attendance
     */
    @Transactional
    public Attendance updateAttendance(Long id, Attendance.Status status, String remarks) {
        Attendance attendance = getAttendanceById(id);
        attendance.setStatus(status);
        attendance.setRemarks(remarks);
        return attendanceRepository.save(attendance);
    }
    
    /**
     * Delete attendance
     */
    @Transactional
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}

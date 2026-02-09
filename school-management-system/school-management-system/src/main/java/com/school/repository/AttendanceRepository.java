package com.school.repository;

import com.school.entity.Attendance;
import com.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * AttendanceRepository - Data access layer for Attendance entity
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    List<Attendance> findByStudent(Student student);
    
    List<Attendance> findByAttendanceDate(LocalDate date);
    
    Optional<Attendance> findByStudentAndAttendanceDate(Student student, LocalDate date);
    
    List<Attendance> findByStudentAndAttendanceDateBetween(Student student, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.status = 'PRESENT'")
    Long countPresentDays(Student student);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student")
    Long countTotalDays(Student student);
}

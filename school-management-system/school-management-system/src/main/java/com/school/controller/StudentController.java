package com.school.controller;

import com.school.entity.*;
import com.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * StudentController - Handles student operations
 */
@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final MarkService markService;
    private final FeeService feeService;
    private final UserService userService;
    
    /**
     * Student dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Student student = studentService.getStudentByUserId(user.getId());
        
        model.addAttribute("student", student);
        return "student/dashboard";
    }
    
    /**
     * View profile
     */
    @GetMapping("/profile")
    public String viewProfile(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Student student = studentService.getStudentByUserId(user.getId());
        
        model.addAttribute("student", student);
        return "student/profile";
    }
    
    /**
     * View attendance
     */
    @GetMapping("/attendance")
    public String viewAttendance(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Student student = studentService.getStudentByUserId(user.getId());
        
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(student);
        Double attendancePercentage = attendanceService.calculateAttendancePercentage(student);
        
        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("attendancePercentage", attendancePercentage);
        return "student/attendance";
    }
    
    /**
     * View marks
     */
    @GetMapping("/marks")
    public String viewMarks(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Student student = studentService.getStudentByUserId(user.getId());
        
        List<Mark> marks = markService.getMarksByStudent(student);
        
        model.addAttribute("marks", marks);
        return "student/marks";
    }
    
    /**
     * View fee status
     */
    @GetMapping("/fees")
    public String viewFees(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Student student = studentService.getStudentByUserId(user.getId());
        
        List<Fee> fees = feeService.getFeesByStudent(student);
        
        model.addAttribute("fees", fees);
        return "student/fees";
    }
}

package com.school.controller;

import com.school.entity.*;
import com.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * TeacherController - Handles teacher operations
 */
@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final AttendanceService attendanceService;
    private final MarkService markService;
    private final StudentService studentService;
    private final ExamService examService;
    private final UserService userService;
    
    /**
     * Teacher dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        
        List<Subject> subjects = subjectService.getSubjectsByTeacher(teacher);
        
        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);
        return "teacher/dashboard";
    }
    
    /**
     * View assigned classes and subjects
     */
    @GetMapping("/subjects")
    public String viewSubjects(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        
        List<Subject> subjects = subjectService.getSubjectsByTeacher(teacher);
        model.addAttribute("subjects", subjects);
        return "teacher/subjects";
    }
    
    // ==================== Attendance Management ====================
    
    /**
     * Show attendance page
     */
    @GetMapping("/attendance")
    public String attendancePage(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        
        List<Subject> subjects = subjectService.getSubjectsByTeacher(teacher);
        model.addAttribute("subjects", subjects);
        model.addAttribute("attendanceDate", LocalDate.now());
        return "teacher/attendance";
    }
    
    /**
     * Get students for marking attendance
     */
    @GetMapping("/attendance/class/{classId}")
    public String getStudentsForAttendance(@PathVariable Long classId, Model model) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(classId);
        
        List<Student> students = studentService.getActiveStudentsByClass(schoolClass);
        model.addAttribute("students", students);
        model.addAttribute("attendanceDate", LocalDate.now());
        return "teacher/attendance-form";
    }
    
    /**
     * Mark attendance
     */
    @PostMapping("/attendance/mark")
    public String markAttendance(
            Authentication authentication,
            @RequestParam Long studentId,
            @RequestParam String status,
            @RequestParam String date) {
        
        User user = userService.getUserByUsername(authentication.getName());
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        Student student = studentService.getStudentById(studentId);
        
        LocalDate attendanceDate = LocalDate.parse(date);
        Attendance.Status attendanceStatus = Attendance.Status.valueOf(status);
        
        attendanceService.markAttendance(student, attendanceDate, attendanceStatus, teacher);
        
        return "redirect:/teacher/attendance";
    }
    
    // ==================== Marks Management ====================
    
    /**
     * Show marks entry page
     */
    @GetMapping("/marks")
    public String marksPage(Model model) {
        List<Exam> exams = examService.getActiveExams();
        model.addAttribute("exams", exams);
        return "teacher/marks";
    }
    
    /**
     * Show marks entry form
     */
    @GetMapping("/marks/exam/{examId}/subject/{subjectId}")
    public String showMarksEntryForm(
            @PathVariable Long examId,
            @PathVariable Long subjectId,
            Model model) {
        
        Exam exam = examService.getExamById(examId);
        Subject subject = subjectService.getSubjectById(subjectId);
        
        List<Student> students = studentService.getActiveStudentsByClass(exam.getSchoolClass());
        
        model.addAttribute("exam", exam);
        model.addAttribute("subject", subject);
        model.addAttribute("students", students);
        
        return "teacher/marks-entry";
    }
    
    /**
     * Enter marks
     */
    @PostMapping("/marks/enter")
    public String enterMarks(
            Authentication authentication,
            @RequestParam Long studentId,
            @RequestParam Long examId,
            @RequestParam Long subjectId,
            @RequestParam Integer marks) {
        
        User user = userService.getUserByUsername(authentication.getName());
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        
        Student student = studentService.getStudentById(studentId);
        Exam exam = examService.getExamById(examId);
        Subject subject = subjectService.getSubjectById(subjectId);
        
        markService.enterMarks(student, exam, subject, marks, teacher);
        
        return "redirect:/teacher/marks";
    }
}

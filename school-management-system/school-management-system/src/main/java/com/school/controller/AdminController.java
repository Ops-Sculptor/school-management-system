package com.school.controller;

import com.school.entity.*;
import com.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AdminController - Handles admin operations
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;
    private final SubjectService subjectService;
    private final ExamService examService;
    private final FeeService feeService;
    
    /**
     * Admin dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalStudents", studentService.getAllStudents().size());
        model.addAttribute("totalTeachers", teacherService.getAllTeachers().size());
        model.addAttribute("totalClasses", schoolClassService.getAllClasses().size());
        model.addAttribute("totalExams", examService.getAllExams().size());
        return "admin/dashboard";
    }
    
    // ==================== Student Management ====================
    
    /**
     * List all students
     */
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "admin/students/list";
    }
    
    /**
     * Show add student form
     */
    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("classes", schoolClassService.getActiveClasses());
        return "admin/students/add";
    }
    
    /**
     * Create new student
     */
    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student, @RequestParam String password) {
        studentService.createStudent(student, password);
        return "redirect:/admin/students";
    }
    
    /**
     * Show edit student form
     */
    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("classes", schoolClassService.getActiveClasses());
        return "admin/students/edit";
    }
    
    /**
     * Update student
     */
    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/admin/students";
    }
    
    /**
     * Delete student
     */
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/admin/students";
    }
    
    // ==================== Teacher Management ====================
    
    /**
     * List all teachers
     */
    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "admin/teachers/list";
    }
    
    /**
     * Show add teacher form
     */
    @GetMapping("/teachers/add")
    public String showAddTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "admin/teachers/add";
    }
    
    /**
     * Create new teacher
     */
    @PostMapping("/teachers/add")
    public String addTeacher(@ModelAttribute Teacher teacher, @RequestParam String password) {
        teacherService.createTeacher(teacher, password);
        return "redirect:/admin/teachers";
    }
    
    /**
     * Show edit teacher form
     */
    @GetMapping("/teachers/edit/{id}")
    public String showEditTeacherForm(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "admin/teachers/edit";
    }
    
    /**
     * Update teacher
     */
    @PostMapping("/teachers/edit/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute Teacher teacher) {
        teacherService.updateTeacher(id, teacher);
        return "redirect:/admin/teachers";
    }
    
    /**
     * Delete teacher
     */
    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/admin/teachers";
    }
    
    // ==================== Class Management ====================
    
    /**
     * List all classes
     */
    @GetMapping("/classes")
    public String listClasses(Model model) {
        List<SchoolClass> classes = schoolClassService.getAllClasses();
        model.addAttribute("classes", classes);
        return "admin/classes/list";
    }
    
    /**
     * Show add class form
     */
    @GetMapping("/classes/add")
    public String showAddClassForm(Model model) {
        model.addAttribute("schoolClass", new SchoolClass());
        return "admin/classes/add";
    }
    
    /**
     * Create new class
     */
    @PostMapping("/classes/add")
    public String addClass(@ModelAttribute SchoolClass schoolClass) {
        schoolClassService.createClass(schoolClass);
        return "redirect:/admin/classes";
    }
    
    /**
     * Delete class
     */
    @GetMapping("/classes/delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClass(id);
        return "redirect:/admin/classes";
    }
    
    // ==================== Subject Management ====================
    
    /**
     * List all subjects
     */
    @GetMapping("/subjects")
    public String listSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "admin/subjects/list";
    }
    
    /**
     * Show add subject form
     */
    @GetMapping("/subjects/add")
    public String showAddSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("classes", schoolClassService.getActiveClasses());
        model.addAttribute("teachers", teacherService.getActiveTeachers());
        return "admin/subjects/add";
    }
    
    /**
     * Create new subject
     */
    @PostMapping("/subjects/add")
    public String addSubject(@ModelAttribute Subject subject) {
        subjectService.createSubject(subject);
        return "redirect:/admin/subjects";
    }
    
    /**
     * Assign teacher to subject
     */
    @PostMapping("/subjects/{subjectId}/assign-teacher")
    public String assignTeacher(@PathVariable Long subjectId, @RequestParam Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        subjectService.assignTeacher(subjectId, teacher);
        return "redirect:/admin/subjects";
    }
    
    // ==================== Fee Management ====================
    
    /**
     * List all fees
     */
    @GetMapping("/fees")
    public String listFees(Model model) {
        List<Fee> fees = feeService.getAllFees();
        model.addAttribute("fees", fees);
        return "admin/fees/list";
    }
    
    /**
     * Show add fee form
     */
    @GetMapping("/fees/add")
    public String showAddFeeForm(Model model) {
        model.addAttribute("fee", new Fee());
        model.addAttribute("students", studentService.getActiveStudents());
        return "admin/fees/add";
    }
    
    /**
     * Create new fee
     */
    @PostMapping("/fees/add")
    public String addFee(@ModelAttribute Fee fee) {
        feeService.createFee(fee);
        return "redirect:/admin/fees";
    }
}

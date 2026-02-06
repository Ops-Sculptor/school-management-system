package com.school.service;

import com.school.dto.ExamResultDTO;
import com.school.entity.ExamResult;
import com.school.entity.Examination;
import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.repository.ExamResultRepository;
import com.school.repository.ExaminationRepository;
import com.school.repository.StudentRepository;
import com.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultService {
    
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final ExaminationRepository examinationRepository;
    private final TeacherRepository teacherRepository;
    
    @Transactional
    public ExamResultDTO enterMarks(ExamResultDTO dto, Long teacherId) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Examination examination = examinationRepository.findById(dto.getExaminationId())
                .orElseThrow(() -> new RuntimeException("Examination not found"));
        
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        // Check if result already exists
        ExamResult examResult = examResultRepository
                .findByStudentIdAndExaminationId(dto.getStudentId(), dto.getExaminationId())
                .orElse(new ExamResult());
        
        examResult.setStudent(student);
        examResult.setExamination(examination);
        examResult.setMarksObtained(dto.getMarksObtained());
        examResult.setGrade(calculateGrade(dto.getMarksObtained(), examination.getMaxMarks()));
        examResult.setStatus(calculateStatus(dto.getMarksObtained(), examination.getPassingMarks()));
        examResult.setRemarks(dto.getRemarks());
        examResult.setEnteredBy(teacher);
        
        examResult = examResultRepository.save(examResult);
        return convertToDTO(examResult);
    }
    
    @Transactional
    public ExamResultDTO updateMarks(Long id, ExamResultDTO dto) {
        ExamResult examResult = examResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam result not found"));
        
        examResult.setMarksObtained(dto.getMarksObtained());
        examResult.setGrade(calculateGrade(dto.getMarksObtained(), examResult.getExamination().getMaxMarks()));
        examResult.setStatus(calculateStatus(dto.getMarksObtained(), examResult.getExamination().getPassingMarks()));
        examResult.setRemarks(dto.getRemarks());
        
        examResult = examResultRepository.save(examResult);
        return convertToDTO(examResult);
    }
    
    public List<ExamResultDTO> getResultsByStudent(Long studentId) {
        return examResultRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ExamResultDTO> getResultsByExamination(Long examinationId) {
        return examResultRepository.findByExaminationId(examinationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private String calculateGrade(Double marks, Integer maxMarks) {
        double percentage = (marks / maxMarks) * 100;
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 50) return "C";
        else if (percentage >= 40) return "D";
        else return "F";
    }
    
    private ExamResult.ResultStatus calculateStatus(Double marks, Integer passingMarks) {
        return marks >= passingMarks ? ExamResult.ResultStatus.PASS : ExamResult.ResultStatus.FAIL;
    }
    
    private ExamResultDTO convertToDTO(ExamResult examResult) {
        ExamResultDTO dto = new ExamResultDTO();
        dto.setId(examResult.getId());
        dto.setStudentId(examResult.getStudent().getId());
        dto.setStudentName(examResult.getStudent().getFirstName() + " " + examResult.getStudent().getLastName());
        dto.setRollNumber(examResult.getStudent().getRollNumber());
        dto.setExaminationId(examResult.getExamination().getId());
        dto.setExamName(examResult.getExamination().getExamName());
        dto.setSubjectName(examResult.getExamination().getSubject().getSubjectName());
        dto.setMaxMarks(examResult.getExamination().getMaxMarks());
        dto.setMarksObtained(examResult.getMarksObtained());
        dto.setGrade(examResult.getGrade());
        dto.setStatus(examResult.getStatus().name());
        dto.setRemarks(examResult.getRemarks());
        
        if (examResult.getEnteredBy() != null) {
            dto.setEnteredByTeacher(examResult.getEnteredBy().getFirstName() + " " + 
                                    examResult.getEnteredBy().getLastName());
        }
        
        return dto;
    }
}

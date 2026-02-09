package com.school.repository;

import com.school.entity.SchoolClass;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * SubjectRepository - Data access layer for Subject entity
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    Optional<Subject> findBySubjectCode(String subjectCode);
    
    List<Subject> findBySchoolClass(SchoolClass schoolClass);
    
    List<Subject> findByActiveTrue();
    
    List<Subject> findBySchoolClassAndActiveTrue(SchoolClass schoolClass);
    
    List<Subject> findByTeachersContaining(Teacher teacher);
    
    boolean existsBySubjectCode(String subjectCode);
}

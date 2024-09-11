package com.aidis.teama.student.service;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.student.model.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor-based dependency injection
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Example method to find a student by ID
    public StudentEntity findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // Example method to save a student
    public StudentEntity saveStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    // Other business methods using studentRepository
    public StudentDTO register(StudentRequest studentRequest) {
        var entity = StudentEntity.builder()
                .token(studentRequest.getToken())
                .child_name(studentRequest.getChildName())
                .behavior_type(studentRequest.getBehaviorName())
                .behavior_name(studentRequest.getBehaviorName())
                .record_type(studentRequest.getBehaviorName())
                .build();

        studentRepository.save(entity);
        return StudentConverter.StudentToDTO(entity);
    }
}
package com.aidis.teama.student.service;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.student.model.StudentAddRequest;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.model.CustomUserDetails;
import com.aidis.teama.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CustomUserDetailsService customUserDetailsService;


    // Constructor-based dependency injection
    @Autowired
    public StudentService(StudentRepository studentRepository, CustomUserDetailsService customUserDetailsService) {
        this.studentRepository = studentRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Other business methods using studentRepository
    public StudentDTO add(StudentAddRequest studentAddRequest) {
        GoogleUserEntity googleUser = customUserDetailsService.getCurrentUser();


        if (googleUser != null) {

            var entity = StudentEntity.builder()
                    .student_name(studentAddRequest.getStudent_name())
                    .birthday(studentAddRequest.getBirthday())
                    .expressionLevel(studentAddRequest.getExpressionLevel())
                    .status(studentAddRequest.getStatus())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .googleUser(googleUser)
                    .build();

            studentRepository.save(entity);
            return StudentConverter.StudentToDTO(entity);
        }
        return null;
    }
}
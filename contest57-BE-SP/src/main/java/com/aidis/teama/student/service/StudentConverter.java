package com.aidis.teama.student.service;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.model.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentConverter {
    public static StudentDTO StudentToDTO(StudentEntity studentEntity) {


        return StudentDTO.builder()
                .id(studentEntity.getId())
                .created_at(studentEntity.getCreatedAt())
                .student_name(studentEntity.getStudent_name())
                .birthday(studentEntity.getBirthday())
                .expressionLevel(studentEntity.getExpressionLevel())
                .status(studentEntity.getStatus())
                .build();
    }
}

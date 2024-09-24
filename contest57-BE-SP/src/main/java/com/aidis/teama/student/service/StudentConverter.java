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
                .child_name(studentEntity.getChild_name())
                .behavior_type(studentEntity.getBehavior_type())
                .behavior_name(studentEntity.getBehavior_name())
                .record_type(studentEntity.getRecord_type())
                .build();
    }
}

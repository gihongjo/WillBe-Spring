package com.aidis.teama.behavior.service;


import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.model.StudentWithBehaviorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordingBehaviorConverter {

    public StudentWithBehaviorDTO BehaviorToRecordingBehaviorDTO(BehaviorEntity behaviorEntity){

        var entity = StudentWithBehaviorDTO.builder()
                .studentId(behaviorEntity.getStudentEntity().getId())
                .behaviorId(behaviorEntity.getId())
                .studentName(behaviorEntity.getStudentEntity().getStudent_name())
                .behaviorName(behaviorEntity.getBehaviorName())
                .behaviorType(behaviorEntity.getBehaviorType())
                .recordType(behaviorEntity.getRecordType())
                .status(behaviorEntity.getStatus())
                .build();
        return entity;

    }
}

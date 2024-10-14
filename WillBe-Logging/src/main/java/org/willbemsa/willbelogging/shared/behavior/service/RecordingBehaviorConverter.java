package org.willbemsa.willbelogging.shared.behavior.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;
import org.willbemsa.willbelogging.shared.behavior.model.StudentWithBehaviorDTO;

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

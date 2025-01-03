package com.aidis.teama.user.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.user.model.ViewStudentBehaviorsDTO;
import com.aidis.teama.user.model.ViewStudentsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserConverter {

    public ViewStudentsDTO viewStudentsConverter(
            StudentEntity studentEntity, List<BehaviorEntity> behaviorEntities, String bhvStatus
    ){
        ViewStudentsDTO viewStudentsDTO = ViewStudentsDTO.builder()
                .studentId(studentEntity.getId())
                .student_name(studentEntity.getStudent_name())
                .expressionLevel(studentEntity.getExpressionLevel())
                .created_at(studentEntity.getCreatedAt())
                .status(studentEntity.getStatus())
                .birthday(studentEntity.getBirthday())
                .viewStudentBehaviorsDTOList(viewBehaviorConverter(behaviorEntities,bhvStatus))
                .build();


        return viewStudentsDTO;

    }


    public List<ViewStudentBehaviorsDTO> viewBehaviorConverter(
            List<BehaviorEntity> behaviorEntities, String bhvStatus
    ){

        List<ViewStudentBehaviorsDTO> viewStudentBehaviorsDTOS= new ArrayList<>();


        for(BehaviorEntity behaviorEntity : behaviorEntities){
            if(behaviorEntity.getStatus().equals(bhvStatus))
            viewStudentBehaviorsDTOS.add(ViewStudentBehaviorsDTO.builder()
                    .behaviorId(behaviorEntity.getId())
                    .behaviorName(behaviorEntity.getBehaviorName())
                            .behaviorStatus(behaviorEntity.getStatus())
                    .build()
            );
        }

        return viewStudentBehaviorsDTOS;

    }


}

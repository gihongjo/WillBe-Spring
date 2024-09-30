package com.aidis.teama.record.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.db.BehaviorRepository;
import com.aidis.teama.record.db.RecordEntity;
import com.aidis.teama.record.db.RecordRepository;
import com.aidis.teama.record.model.RecordLogsDTO;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.service.CustomUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class RecordService {

    private final RecordRepository recordRepository;
    private final BehaviorRepository behaviorRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final RecordToLogDTOConverter recordToLogDTOConverter;

    public RecordService(RecordRepository recordRepository, BehaviorRepository behaviorRepository, CustomUserDetailsService customUserDetailsService, RecordToLogDTOConverter recordToLogDTOConverter) {
        this.recordRepository = recordRepository;
        this.behaviorRepository = behaviorRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.recordToLogDTOConverter = recordToLogDTOConverter;
    }


    public RecordLogsDTO RecordAndGetLogs(String behavior_id){

        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();
        Optional<BehaviorEntity> optBehaviorEntity= behaviorRepository.findById(Long.valueOf(behavior_id));

        List<RecordEntity> recordEntityList = new ArrayList<>();

        if (optBehaviorEntity.isPresent()){
            BehaviorEntity behaviorEntity = optBehaviorEntity.get();

            if(googleUserEntity.getUserId().equals(behaviorEntity.getStudentEntity().getGoogleUser().getUserId())){

                RecordEntity recordEntity= RecordEntity.builder()
                        .behaviorEntity(behaviorEntity)
                        .time(LocalDateTime.now())
                        .build();

                recordRepository.save(recordEntity);



                return recordToLogDTOConverter.recordEntityToDTO(recordEntity);


            }

            throw new IllegalStateException("해당 유저가 조작할 수 있는 행동이 아닙니다.");
        }


        throw new IllegalStateException("행동 id 맞지 않음");

    }



}
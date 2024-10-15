package org.willbemsa.willbelogging.record.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.willbemsa.willbelogging.record.db.RecordEntity;
import org.willbemsa.willbelogging.record.db.RecordRepository;
import org.willbemsa.willbelogging.record.model.GraphDailyDTO;
import org.willbemsa.willbelogging.record.model.RecordLogsDTO;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorRepository;
import org.willbemsa.willbelogging.shared.student.db.StudentRepository;
import org.willbemsa.willbelogging.shared.user.db.GoogleUserEntity;
import org.willbemsa.willbelogging.shared.user.service.CustomUserDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class RecordService {

    private final RecordRepository recordRepository;
    private final BehaviorRepository behaviorRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final RecordConverter recordConverter;

    private final StudentRepository studentRepository;


    public RecordService(RecordRepository recordRepository, BehaviorRepository behaviorRepository, CustomUserDetailsService customUserDetailsService, RecordConverter recordConverter, StudentRepository studentRepository) {
        this.recordRepository = recordRepository;
        this.behaviorRepository = behaviorRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.recordConverter = recordConverter;
        this.studentRepository = studentRepository;
    }


    public RecordLogsDTO RecordAndGetLogs(String behavior_id){

        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();
        Optional<BehaviorEntity> optBehaviorEntity = behaviorRepository.findById(Long.valueOf(behavior_id));

        List<RecordEntity> recordEntityList = new ArrayList<>();

        if (optBehaviorEntity.isPresent()) {
            BehaviorEntity behaviorEntity = optBehaviorEntity.get();

            if (googleUserEntity.getUserId().equals(behaviorEntity.getStudentEntity().getGoogleUser().getUserId())) {

                RecordEntity recordEntity = RecordEntity.builder()
                        .behaviorEntity(behaviorEntity)
                        .time(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()) // 한국 시간으로 설정
                        .build();

                recordRepository.save(recordEntity);

                return recordConverter.recordEntityToDTO(recordEntity);
            }

            throw new IllegalStateException("해당 유저가 조작할 수 있는 행동이 아닙니다.");
        }

        throw new IllegalStateException("행동 id 맞지 않음");
    }



    public List<RecordLogsDTO> getDailyBehaviorLogs(){

        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();

        List<RecordLogsDTO> recordLogsDTOList = new ArrayList<>();


        List<RecordEntity>recordEntityList= recordRepository.findTodayRecordingdRecordEntitiesByGoogleUserId(googleUserEntity.getId());

        recordLogsDTOList.addAll(
                recordEntityList.stream()
                        .map(recordConverter::recordEntityToDTO)
                        .collect(Collectors.toList())
        );

        return recordLogsDTOList;
    }

    public GraphDailyDTO getGraphDaily(
            String behavior_id, LocalDate date
    ){

        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();


        GraphDailyDTO graphDailyDTO = null;

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);


        List<RecordEntity> recordEntityList= recordRepository.findRecordingRecordsByGoogleUserIdAndBehaviorIdAndDate(
                googleUserEntity.getId(),Long.valueOf(behavior_id), startOfDay,endOfDay);

         return recordConverter.convertToGraphDailyDTO(recordEntityList);


    }



    public boolean deleteRecord(String recordId){


        Optional<RecordEntity> optRecordEntity=   recordRepository.findById(Long.valueOf(recordId));


        if(optRecordEntity.isPresent()){
                RecordEntity recordEntity = optRecordEntity.get();

                if(recordEntity.getBehaviorEntity().getStudentEntity().getGoogleUser().getId().equals
                        (customUserDetailsService.getCurrentUser().getId())){
                    recordRepository.deleteById(Long.valueOf(recordId));
                    return true;
                }


        }



        return false;

    }

}
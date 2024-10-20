package com.aidis.teama.record.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.db.BehaviorRepository;
import com.aidis.teama.record.db.RecordEntity;
import com.aidis.teama.record.db.RecordRepository;
import com.aidis.teama.record.model.GraphDailyDTO;
import com.aidis.teama.record.model.GraphWeeklyDTO;
import com.aidis.teama.record.model.RecordLogsDTO;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.service.CustomUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

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
    ) throws IllegalAccessException {

        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();

        Optional<BehaviorEntity> optBehaviorEntity =behaviorRepository.findById(Long.valueOf(behavior_id));

        if(optBehaviorEntity.isPresent()) {
            if(optBehaviorEntity.get().getStudentEntity().getStudent_name().equals(googleUserEntity.getUserName())){

                GraphDailyDTO graphDailyDTO = null;

                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(23, 59, 59);


                List<RecordEntity> recordEntityList= recordRepository.findRecordingRecordsByGoogleUserIdAndBehaviorIdAndDate(
                        googleUserEntity.getId(),Long.valueOf(behavior_id), startOfDay,endOfDay);

                return recordConverter.convertToGraphDailyDTO(recordEntityList);

            }else             throw new IllegalAccessException("해당 유저는 해당 행동에 대한 권한이 없습니다.");


        }else
            throw new IllegalAccessException("해당 행동이 없습니다.");


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


    public GraphWeeklyDTO getGraphWeekly(String behaviorId, LocalDate endDate) {
        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();




        // 일주일 전 날짜 계산 (오늘 포함 7일)
        LocalDate startDate = endDate.minusDays(6);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // 레코드 조회
        List<RecordEntity> recordEntities = recordRepository
                .findRecordingRecordsByGoogleUserIdAndBehaviorIdBetweenDates(
                        googleUserEntity.getId(),
                        Long.valueOf(behaviorId),
                        startDateTime,
                        endDateTime
                );

        // 날짜별 카운트 초기화
        List<Integer> dailyCounts = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dailyCounts.add(0);
        }

        // 각 레코드를 해당 날짜에 카운트
        for (RecordEntity record : recordEntities) {
            LocalDate recordDate = record.getTime().toLocalDate();
            long daysBetween = startDate.until(recordDate).getDays();
            if (daysBetween >= 0 && daysBetween < 7) {
                dailyCounts.set((int) daysBetween, dailyCounts.get((int) daysBetween) + 1);
            }
        }

        return new GraphWeeklyDTO(dailyCounts);
    }

}

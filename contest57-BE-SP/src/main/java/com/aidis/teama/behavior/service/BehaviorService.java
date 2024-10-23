package com.aidis.teama.behavior.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.db.BehaviorRepository;
import com.aidis.teama.behavior.model.BehaviorAddRequest;
import com.aidis.teama.behavior.model.BehaviorFinishRequest;
import com.aidis.teama.behavior.model.StudentWithBehaviorDTO;
import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.user.db.GoogleUserRepository;
import com.aidis.teama.user.service.CustomUserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class BehaviorService {

    private final BehaviorRepository behaviorRepository;

    private final StudentRepository studentRepository;

    private final GoogleUserRepository googleUserRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final RecordingBehaviorConverter recordingBehaviorConverter;

    public BehaviorService(BehaviorRepository behaviorRepository, StudentRepository studentRepository, GoogleUserRepository googleUserRepository, CustomUserDetailsService customUserDetailsService, RecordingBehaviorConverter recordingBehaviorConverter) {
        this.behaviorRepository = behaviorRepository;

        this.studentRepository = studentRepository;
        this.googleUserRepository = googleUserRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.recordingBehaviorConverter = recordingBehaviorConverter;
    }


    //행동 생성 시
    public String BehaviorAdd(BehaviorAddRequest behaviorAddRequest) {
        if(isRecordingBehaviorOver6()==true)

            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "기록하는 행동의 수가 6개를 초과할 수 없습니다.");


        try {
            var googleUserEntity= customUserDetailsService.getCurrentUser();



            Optional<StudentEntity> optionalStudentEntity = studentRepository.findById(behaviorAddRequest.getStudentId());

            if (optionalStudentEntity.isPresent()) {

                StudentEntity studentEntity = optionalStudentEntity.get();

                //해당 아동이 같은 이름의 행동을 가지고 있는지 판별한다.

                List<BehaviorEntity> behaviorEntities =behaviorRepository.findAllByStudentEntity(studentEntity);

                boolean behavior= behaviorEntities.stream()
                        .filter(behaviorEntity -> behaviorEntity.getBehaviorName().equals(behaviorAddRequest
                                .getBehaviorName())).findFirst().isPresent();

                if(behavior){
                    return ("해당 아동은 이미 같은 이름의 행동을 가지고 있습니다.");

                }

                if(studentEntity.getGoogleUser().getId().equals(googleUserEntity.getId())) {


                    var behaviorEntity = BehaviorEntity.builder()
                            .behaviorName(behaviorAddRequest.getBehaviorName())
                            .behaviorType(behaviorAddRequest.getBehaviorType())
                            .recordType(behaviorAddRequest.getRecordType())
                            .studentEntity(studentEntity)
                            .status(behaviorAddRequest.getStatus())
                            .build();

                    behaviorRepository.save(behaviorEntity);
                    return "Behavior added successfully.";
                } else {
                    throw new IllegalStateException("Unauthorized to add behavior for this student.");
                }
            } else {
                throw new IllegalArgumentException("Student not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add behavior: " + e.getMessage(), e);
        }
    }

    public List<StudentWithBehaviorDTO> getStudentsBehaviorsByStatusList(String studentId,String bhvStatus){

        List<StudentWithBehaviorDTO> studentWithBehaviorDTOList = new ArrayList<>();

        Optional<StudentEntity> optStudentEntity= studentRepository.findById(Long.valueOf(studentId));

        if(optStudentEntity.isPresent()){

            StudentEntity studentEntity=optStudentEntity.get();
            List<BehaviorEntity> behaviorEntityList = behaviorRepository.findAllByStudentEntityAndStatus(studentEntity,bhvStatus);

            for(BehaviorEntity behaviorEntity : behaviorEntityList){
                studentWithBehaviorDTOList.add(recordingBehaviorConverter.BehaviorToRecordingBehaviorDTO(behaviorEntity));
            }

        }

        return studentWithBehaviorDTOList;

    }


    public List<StudentWithBehaviorDTO> getRecordingBehaviorsByStatusList(String bhvStatus){

        List<StudentWithBehaviorDTO> studentWithBehaviorDTOList = new ArrayList<>();

        List<StudentEntity> studentEntityList=
                studentRepository.findByGoogleUserOrderByCreatedAtDesc(customUserDetailsService.getCurrentUser());

        for(StudentEntity studentEntity :studentEntityList){

            List<BehaviorEntity> behaviorEntityList =
                    behaviorRepository.findAllByStudentEntityAndStatus(studentEntity,bhvStatus);

            for(BehaviorEntity behaviorEntity: behaviorEntityList){

                studentWithBehaviorDTOList
                        .add(recordingBehaviorConverter.BehaviorToRecordingBehaviorDTO(behaviorEntity));

            }
        }

        return studentWithBehaviorDTOList;

    }


    public String setBehaviorStatus(
            String bhv_id,
            String status,
            BehaviorFinishRequest behaviorFinishRequest
    ) {

        if(status.equals("recording")){
            if(isRecordingBehaviorOver6()==true){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "기록하는 행동의 수가 6개를 초과할 수 없습니다.");
            }

        }

        if(behaviorRepository.findById(Long.valueOf(bhv_id)).get().getStudentEntity()
                .getGoogleUser().getUserName().equals(customUserDetailsService.getCurrentUser().getUserName())==false){
            throw new IllegalStateException("권한 없음");
        }



        int updatedCount= behaviorRepository.updateStatusByBehaviorId(
                Long.valueOf(bhv_id),
                status,
                behaviorFinishRequest.overDescription,
                behaviorFinishRequest.overMeaning,
                behaviorFinishRequest.overMeasures);

        if(updatedCount == 0){
            throw new EntityNotFoundException("BehaviorEntity with id " + Long.valueOf(bhv_id)  + " not found.");
        }

        return "완료";
    }




    public boolean isRecordingBehaviorOver6(){
        int bhvCount=0;

        List<StudentEntity> studentEntityList
                = studentRepository.findAllByGoogleUser(customUserDetailsService.getCurrentUser());
        for(StudentEntity studentEntity:studentEntityList){
            List<BehaviorEntity> behaviorEntity= behaviorRepository.findAllByStudentEntityAndStatus(studentEntity,"recording");
            bhvCount+=behaviorEntity.size();
            log.info(behaviorEntity.toString());
            if(bhvCount>=6)
                return true;
        }
        if(bhvCount>=6)
            return true;
        return false;
    }

}

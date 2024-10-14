package org.willbemsa.willbelogging.shared.behavior.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorRepository;
import org.willbemsa.willbelogging.shared.behavior.model.BehaviorAddRequest;
import org.willbemsa.willbelogging.shared.behavior.model.StudentWithBehaviorDTO;
import org.willbemsa.willbelogging.shared.student.db.StudentEntity;
import org.willbemsa.willbelogging.shared.student.db.StudentRepository;
import org.willbemsa.willbelogging.shared.user.db.GoogleUserRepository;
import org.willbemsa.willbelogging.shared.user.service.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public ResponseEntity<String> BehaviorAdd(BehaviorAddRequest behaviorAddRequest) {
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
                    throw new IllegalStateException("해당 아동은 이미 같은 이름의 행동을 가지고 있습니다.");

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
                    return ResponseEntity.ok("Behavior added successfully.");
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

    public List<StudentWithBehaviorDTO> getRecordingBehaviorList(){

        List<StudentWithBehaviorDTO> studentWithBehaviorDTOList = new ArrayList<>();

        List<StudentEntity> studentEntityList=
                studentRepository.findByGoogleUserOrderByCreatedAtDesc(customUserDetailsService.getCurrentUser());

        for(StudentEntity studentEntity :studentEntityList){

            List<BehaviorEntity> behaviorEntityList =
                    behaviorRepository.findAllByStudentEntityAndAndStatus(studentEntity,"recording");

            for(BehaviorEntity behaviorEntity: behaviorEntityList){

                studentWithBehaviorDTOList
                        .add(recordingBehaviorConverter.BehaviorToRecordingBehaviorDTO(behaviorEntity));

            }
        }

        return studentWithBehaviorDTOList;

    }

}

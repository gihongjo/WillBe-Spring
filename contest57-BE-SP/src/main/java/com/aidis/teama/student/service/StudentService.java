package com.aidis.teama.student.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.db.BehaviorRepository;
import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.student.model.FirstStudentAddRequest;
import com.aidis.teama.student.model.StudentAddRequest;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final BehaviorRepository behaviorRepository;
    private final CustomUserDetailsService customUserDetailsService;


    // Constructor-based dependency injection
    @Autowired
    public StudentService(StudentRepository studentRepository, BehaviorRepository behaviorRepository, CustomUserDetailsService customUserDetailsService) {
        this.studentRepository = studentRepository;
        this.behaviorRepository = behaviorRepository;
        this.customUserDetailsService = customUserDetailsService;
    }


    public String firstStudentAdd(
            FirstStudentAddRequest firstStudentAddRequest
    ){

        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();

        var studentEntity = StudentEntity.builder()
                .student_name(firstStudentAddRequest.getStudentName())
                .birthday(firstStudentAddRequest.getBirthday())
                .expressionLevel(firstStudentAddRequest.getExpressionLevel())
                .status(firstStudentAddRequest.getStudentStatus())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .googleUser(googleUserEntity)
                .build();

        studentRepository.save(studentEntity);




        var behaviorEntity = BehaviorEntity.builder()
                .id(studentEntity.getId())
                .behaviorName(firstStudentAddRequest.getBehaviorName())
                .behaviorType(firstStudentAddRequest.getBehaviorType())
                .recordType(firstStudentAddRequest.getRecordType())
                .studentEntity(studentEntity)
                .status(firstStudentAddRequest.getBehaviorStatus())
                .build();

        behaviorRepository.save(behaviorEntity);

        return "first student with behavior saved";

    }


    public ResponseEntity<String> add(StudentAddRequest studentAddRequest) {


        GoogleUserEntity googleUserEntity = customUserDetailsService.getCurrentUser();
        if (googleUserEntity != null) {

        Optional<StudentEntity> foundStudent = googleUserEntity.getStudents().stream()
                .filter(student -> student.getStudent_name().equals(studentAddRequest.getStudent_name()))
                .findFirst();

        if(foundStudent.isPresent()){

            throw new IllegalStateException("사용자는 이미 똑같은 이름을 가진 학생을 가지고 있습니다. 다른 이름으로 생성해주세요.");
        }
            var entity = StudentEntity.builder()
                    .student_name(studentAddRequest.getStudent_name())
                    .birthday(studentAddRequest.getBirthday())
                    .expressionLevel(studentAddRequest.getExpressionLevel())
                    .status(studentAddRequest.getStatus())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .googleUser(googleUserEntity)
                    .build();

            studentRepository.save(entity);
            return ResponseEntity.ok("Student added successfully");


        }else {
            throw new IllegalStateException("JWT Error");
        }
    }
}
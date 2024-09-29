package com.aidis.teama.user.service;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.student.service.StudentConverter;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.db.GoogleUserRepository;
import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.util.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final GoogleUserRepository googleUserRepository;
    private final StudentRepository studentRepository;



    public String GoogleLoginService(
            GoogleRegisterRequest googleLoginRequest
    ) {

        String jwt;

        try {
            Optional<GoogleUserEntity> googleUserEntity = googleUserRepository.findByEmail(googleLoginRequest.getEmail());

            if (googleUserEntity.isPresent()) {
                jwt = jwtTokenProvider.createToken(googleLoginRequest.getEmail());

                return jwt;

            } else {

                var entity = GoogleUserEntity.builder()
                        .userName(googleLoginRequest.getUserName())
                        .email(googleLoginRequest.getEmail())
                        .userId(googleLoginRequest.getUserId())
                        .createdAt(LocalDateTime.now())
                        .build();
                googleUserRepository.save(entity);




                jwt = jwtTokenProvider.createToken(entity.getEmail());

                return "registered_"+jwt;

            }
        } catch (Exception e) {
            log.info(e.toString());
        }
        return "Error";
    }


    public List<StudentDTO> ViewStudents(
            GoogleUserEntity googleUserEntity
    ){
        List<StudentEntity> studentEntityList = studentRepository.findByGoogleUserOrderByCreatedAtDesc(googleUserEntity);
        List<StudentDTO> studentDTOS = new ArrayList<>(); // 리스트 초기화

        for(int i=0;i< studentEntityList.size();i++){
            studentDTOS.add(i, StudentConverter.StudentToDTO(studentEntityList.get(i)));
        }

        return studentDTOS;
    }

}
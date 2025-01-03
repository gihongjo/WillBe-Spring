package com.aidis.teama.user.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.db.BehaviorRepository;
import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.db.StudentRepository;
import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.student.service.StudentConverter;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.db.GoogleUserRepository;
import com.aidis.teama.user.model.CustomUserDetails;
import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.user.model.ViewStudentsDTO;
import com.aidis.teama.util.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserConverter userConverter;


    public String GoogleLoginService(
            GoogleRegisterRequest googleLoginRequest
    ) {

        String jwt;

        try {
            Optional<GoogleUserEntity> optGoogleUserEntity = googleUserRepository.findByEmail(googleLoginRequest.getEmail());


            if (optGoogleUserEntity.isPresent()) {
                GoogleUserEntity googleUserEntity = optGoogleUserEntity.get();


                jwt = jwtTokenProvider.createToken(googleLoginRequest.getEmail());

                if (googleUserRepository.findByEmailAndCheckStudentExist(googleUserEntity))
                    return "registered_" + jwt;

                return jwt;


                //이미 가입된 정보이기때문에 registered_를 붙여준다. 플러터쪽에서 필요한 것.

            } else {

                var entity = GoogleUserEntity.builder()
                        .userName(googleLoginRequest.getUserName())
                        .email(googleLoginRequest.getEmail())
                        .userId(googleLoginRequest.getUserId())
                        .createdAt(LocalDateTime.now())
                        .build();
                googleUserRepository.save(entity);


                jwt = jwtTokenProvider.createToken(entity.getEmail());

                return jwt;

            }
        } catch (Exception e) {
            log.info(e.toString());
        }
        return "Error";
    }


    public List<StudentDTO> ViewStudents(
            GoogleUserEntity googleUserEntity
    ) {
        List<StudentEntity> studentEntityList = studentRepository.findByGoogleUserOrderByCreatedAtDesc(googleUserEntity);
        List<StudentDTO> studentDTOS = new ArrayList<>(); // 리스트 초기화

        for (int i = 0; i < studentEntityList.size(); i++) {
            studentDTOS.add(i, StudentConverter.StudentToDTO(studentEntityList.get(i)));
        }

        return studentDTOS;
    }


    public List<ViewStudentsDTO> ViewStudentsBehaviors(
            GoogleUserEntity googleUserEntity, String bhvStatus
    ) {
        List<StudentEntity> studentEntityList = studentRepository.findByGoogleUserOrderByCreatedAtDesc(googleUserEntity);

        List<ViewStudentsDTO> viewStudentsDTOList = new ArrayList<>(); // 리스트 초기화


        for (StudentEntity studentEntity : studentEntityList) {

            viewStudentsDTOList.add(userConverter.viewStudentsConverter(studentEntity, studentEntity.getBehaviors(),bhvStatus));

        }
        return viewStudentsDTOList;
    }


    public String autologin() {

        //헤더에서 토큰을 추출해 유저엔터티를 찾는 로직.
        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            GoogleUserEntity googleUser = userDetails.getUser();

            if (googleUserRepository.findByEmailAndCheckStudentExist(googleUser)) {
                return "아동이 있음";
            }
            else{
                throw new IllegalStateException("유저에게 생성된 아동이 없음");

            }
        }
        else {
            throw new IllegalStateException("유저 정보 불일치");
        }
    }


}
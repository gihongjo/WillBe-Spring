package com.aidis.teama.user.service;

import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.db.GoogleUserRepository;
import com.aidis.teama.user.db.UserEntity;
import com.aidis.teama.user.db.UserRepository;
import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.user.model.LoginRequest;
import com.aidis.teama.user.model.UserDTO;
import com.aidis.teama.user.model.UserRequest;
import com.aidis.teama.util.Jwt.JwtTokenProvider;
import com.aidis.teama.util.Jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final GoogleUserRepository googleUserRepository;





    public UserDTO register(UserRequest userRequest) {
        var entity = UserEntity.builder()
                .userName(userRequest.getUserName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(entity);
        return userConverter.userToDTO(entity);
    }


    public String GoogleLoginService(
            GoogleRegisterRequest googleLoginRequest
    ){
//        googleLoginRequest.getEmail()
        var entity = GoogleUserEntity.builder()
                .userName(googleLoginRequest.getUserName())
                .email(googleLoginRequest.getEmail())
                .userId(googleLoginRequest.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
        googleUserRepository.save(entity);
        return "성공";
    }






    public TokenResponse login(LoginRequest loginRequest) {
        log.info(loginRequest.toString());

        Optional<UserEntity> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            log.info("입력받은 비밀번호: " + loginRequest.getPassword());
            log.info("데이터베이스에 있는 비밀번호: " + user.getPassword());

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String jwtToken = jwtTokenProvider.createToken(user.getEmail());
                return new TokenResponse(jwtToken);
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}

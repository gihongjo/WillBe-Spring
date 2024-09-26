package com.aidis.teama.user.controller;

import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.model.CustomUserDetails;
import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.user.model.UserDTO;
import com.aidis.teama.user.model.UserRequest;
import com.aidis.teama.user.service.UserService;
import com.aidis.teama.util.Jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    @Autowired
    private UserService userService;


    final private JwtTokenProvider jwtTokenProvider;



    @PostMapping(value = "/register")
    public UserDTO register(
            @Validated
            @RequestBody
            UserRequest userRequest
    ){
        log.info(userRequest.toString());
        return userService.register(userRequest);
    }


    @PostMapping(value = "/login/google")
    public String loginGoogle(
            @Validated
            @RequestBody
            GoogleRegisterRequest googleRegisterRequest
    ){
        log.info(googleRegisterRequest.toString());
        userService.GoogleLoginService(googleRegisterRequest);

        String jwt=jwtTokenProvider.createToken(googleRegisterRequest.getEmail());

        log.info(jwt);
        return jwt;
    }

    @GetMapping(value = "/login")
    public String login(HttpServletRequest request){

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // GoogleUserEntity에 접근
            GoogleUserEntity googleUser = userDetails.getUser();




            return "Login confirmed for user: " +googleUser.getId().toString();
        } else {
            return "Login failed";
        }


    }

}

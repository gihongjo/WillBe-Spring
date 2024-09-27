package com.aidis.teama.user.controller;

import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.db.GoogleUserRepository;
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



    @PostMapping(value = "/login")
    public String login(
            @Validated
            @RequestBody
            GoogleRegisterRequest googleRegisterRequest
    ){



        String jwt= userService.GoogleLoginService(googleRegisterRequest);


        return jwt;



    }

    @GetMapping(value = "/autologin")
    public String autologin(HttpServletRequest request){

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // GoogleUserEntity에 접근
            GoogleUserEntity googleUser = userDetails.getUser();



            //추후

            return "Login confirmed for user: " +googleUser.toString();
        } else {
            return "Login failed";
        }


    }

}

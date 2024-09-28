package com.aidis.teama.user.controller;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.model.CustomUserDetails;
import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.user.service.CustomUserDetailsService;
import com.aidis.teama.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    final private UserService userService;

    final private CustomUserDetailsService customUserDetailsService;


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
    public String autologin(){

        //헤더에서 토큰을 추출해 유저엔터티를 찾는 로직.
        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            GoogleUserEntity googleUser = userDetails.getUser();

            return googleUser.getUserId();

        }else{
            return "jwt is expired or error";
        }

    }

    @GetMapping(value = "/view_students")
    public List<StudentDTO> viewStudents(){

        GoogleUserEntity googleUserEntity= customUserDetailsService.getCurrentUser();
        return userService.ViewStudents(googleUserEntity);

    }

}

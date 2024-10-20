package com.aidis.teama.user.controller;

import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.user.model.ViewStudentsDTO;
import com.aidis.teama.user.service.CustomUserDetailsService;
import com.aidis.teama.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public String autologin(
    ){


       return userService.autologin();
    }

    @GetMapping(value = "/view_students")
    public List<StudentDTO> viewStudents(){

        GoogleUserEntity googleUserEntity= customUserDetailsService.getCurrentUser();
        return userService.ViewStudents(googleUserEntity);

    }

    @GetMapping(value = "/view_students/behaviors")
    public List<ViewStudentsDTO> viewStudentsAndBehaviors(

    ){

        GoogleUserEntity googleUserEntity= customUserDetailsService.getCurrentUser();
        return userService.ViewStudentsBehaviors(googleUserEntity,"recording");

    }

}

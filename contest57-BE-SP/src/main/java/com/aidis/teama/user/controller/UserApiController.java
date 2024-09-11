package com.aidis.teama.user.controller;

import com.aidis.teama.user.model.GoogleRegisterRequest;
import com.aidis.teama.user.model.UserDTO;
import com.aidis.teama.user.model.UserRequest;
import com.aidis.teama.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public UserDTO register(
            @Validated
            @RequestBody
            UserRequest userRequest
    ) {
        log.info(userRequest.toString());
        return userService.register(userRequest);
    }

//    @PostMapping("/login")
//    public ResponseEntity<TokenResponse> login(
//            @RequestBody
//            LoginRequest loginRequest) {
//        try {
//            TokenResponse tokenResponse = userService.login(loginRequest);
//            log.info(tokenResponse.toString());
//            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            log.info(e.toString());
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }

    @PostMapping(value = "/login/google")
    public String loginGoogle(
            @Validated
            @RequestBody
            GoogleRegisterRequest googleRegisterRequest
    ){
        log.info(googleRegisterRequest.toString());
        userService.GoogleLoginService(googleRegisterRequest);
            return "is it fixed?";
    }
}
package com.aidis.teama.user.controller;

import com.aidis.teama.user.model.LoginRequest;
import com.aidis.teama.user.model.UserRequest;
import com.aidis.teama.user.service.UserService;
import com.aidis.teama.util.Jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String register(
            @Validated
            @RequestBody
            UserRequest userRequest
    ) {
        log.info(userRequest.toString());
        userService.register(userRequest);
        return "HELLO";
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
}

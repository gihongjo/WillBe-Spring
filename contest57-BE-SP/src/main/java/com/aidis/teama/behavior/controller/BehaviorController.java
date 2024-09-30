package com.aidis.teama.behavior.controller;

import com.aidis.teama.behavior.model.BehaviorAddRequest;
import com.aidis.teama.behavior.model.StudentWithBehaviorDTO;
import com.aidis.teama.behavior.service.BehaviorService;
import com.aidis.teama.user.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/behavior")
public class BehaviorController {

    private final BehaviorService behaviorService;

    private final CustomUserDetailsService customUserDetailsService;


    public BehaviorController(BehaviorService behaviorService, CustomUserDetailsService customUserDetailsService) {
        this.behaviorService = behaviorService;
        this.customUserDetailsService = customUserDetailsService;

    }


    @PostMapping(value = "/add")
    public ResponseEntity<String> BehaviorAdd(
            @RequestBody
            BehaviorAddRequest behaviorAddRequest
    ){

        return behaviorService.BehaviorAdd(behaviorAddRequest);

    }

    @GetMapping(value = "/recording_cards")
    public List<StudentWithBehaviorDTO> GetRecording(){


        return behaviorService.getRecordingBehaviorList();

    }







}

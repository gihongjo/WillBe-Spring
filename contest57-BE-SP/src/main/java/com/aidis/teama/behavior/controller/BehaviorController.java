package com.aidis.teama.behavior.controller;

import com.aidis.teama.behavior.model.BehaviorAddRequest;
import com.aidis.teama.behavior.model.BehaviorFinishRequest;
import com.aidis.teama.behavior.model.StudentWithBehaviorDTO;
import com.aidis.teama.behavior.service.BehaviorService;
import com.aidis.teama.user.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
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
    public String BehaviorAdd(
            @RequestBody
            BehaviorAddRequest behaviorAddRequest
    ){

        return behaviorService.BehaviorAdd(behaviorAddRequest);

    }

    @GetMapping(value = "/{student_id}/status/{behavior_status}")
    public List<StudentWithBehaviorDTO> GetBehavioerByStatus(
            @PathVariable(value = "student_id")
            String studentId,
            @PathVariable(value = "behavior_status")
            String bhvStatus
    ){

        return behaviorService.getRecordingBehaviorsByStatusList(studentId,bhvStatus);

    }




    @PostMapping(value = "/{behavior_id}/status_change/{status}")
    public String setBehaviorStatus(
            @PathVariable(value = "behavior_id")
            String behavior_id,
            @PathVariable(value = "status")
            String status,
            @RequestBody
            BehaviorFinishRequest behaviorFinishRequest


    ){


        return behaviorService.setBehaviorStatus(behavior_id, status,behaviorFinishRequest);
    }






}

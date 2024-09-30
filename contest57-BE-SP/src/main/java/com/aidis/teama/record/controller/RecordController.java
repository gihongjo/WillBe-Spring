package com.aidis.teama.record.controller;

import com.aidis.teama.record.db.RecordRepository;
import com.aidis.teama.record.model.RecordLogsDTO;
import com.aidis.teama.record.service.RecordService;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/record")
public class RecordController {


    final private RecordService recordService;



    public RecordController(CustomUserDetailsService customUserDetailsService, RecordRepository recordRepository, RecordService recordService) {
        this.recordService = recordService;
    }


    @GetMapping(value = "/record_logs/{behavior_id}")
    public RecordLogsDTO recordAndGetLogs(
            @PathVariable("behavior_id")
            String behavior_id
    ){


        return recordService.RecordAndGetLogs(behavior_id);




    }


}

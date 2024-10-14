package org.willbemsa.willbelogging.record.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.willbemsa.willbelogging.record.db.RecordRepository;
import org.willbemsa.willbelogging.record.model.GraphDailyDTO;
import org.willbemsa.willbelogging.record.model.RecordLogsDTO;
import org.willbemsa.willbelogging.record.service.RecordService;
import org.willbemsa.willbelogging.shared.user.service.CustomUserDetailsService;

import java.time.LocalDate;
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

    @GetMapping(value = "/daily_logs")
    public List<RecordLogsDTO> getDailyBehaviorLogs(){

        return recordService.getDailyBehaviorLogs();

    }

    @GetMapping(value = "/graph/daily/{behavior_id}")
    public GraphDailyDTO getGraphDaily(

            @PathVariable("behavior_id")
            String behavior_id
    ){

        return recordService.getGraphDaily(behavior_id, LocalDate.now());


    }


    @GetMapping(value = "/{record_id}/delete")
    public boolean deleteRecord(
            @PathVariable(value = "record_id")
            String record_id
    ){

        return recordService.deleteRecord(record_id);


    }



}
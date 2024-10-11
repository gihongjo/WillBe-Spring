package com.aidis.teama.notes.controller;


import com.aidis.teama.notes.db.DailyNoteEntity;
import com.aidis.teama.notes.model.DailyNoteAddRequest;
import com.aidis.teama.notes.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("api/")
public class NotesController {

    final private NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping(value = "/behavior/{behavior_id}/dailyNoteAdd")
    public String DailyNoteAdd(

            @PathVariable(value = "behavior_id")
            String behavior_id,

            @RequestBody
            DailyNoteAddRequest dailyNoteAddRequest
    ){

        if(noteService.addDailyNote(dailyNoteAddRequest,behavior_id)){
            return "데일리노트 저장 완료";
        }else {

            return "데일리 노트 저장 실패: BehaviorAdd 메소드 실패";
        }


    }

    @PostMapping(value = "/behavior/{behavior_id}/dailyNoteView/date/{date}")
    public DailyNoteEntity DailyNoteView(

            @PathVariable(value = "behavior_id")
            String behavior_id,
            @PathVariable(value = "date")
                    String date


    ){

        return noteService.viewDailyNote(behavior_id, LocalDate.parse(date));


    }



}

package com.aidis.teama.notes.service;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.behavior.db.BehaviorRepository;
import com.aidis.teama.notes.db.DailyNoteEntity;
import com.aidis.teama.notes.db.DailyNoteRepository;
import com.aidis.teama.notes.model.DailyNoteAddRequest;
import com.aidis.teama.user.service.CustomUserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NoteService {

    private final CustomUserDetailsService customUserDetailsService;
    private final DailyNoteRepository dailyNoteRepository;


    public NoteService(CustomUserDetailsService customUserDetailsService, BehaviorRepository behaviorRepository, DailyNoteRepository dailyNoteRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.dailyNoteRepository = dailyNoteRepository;
    }


    public boolean addDailyNote(
            DailyNoteAddRequest dailyNoteAddRequest, String behaviorId
    ){


        BehaviorEntity behaviorEntity =
                customUserDetailsService
                        .userHasBehavior(customUserDetailsService.getCurrentUser(), Long.valueOf(behaviorId));
        if (behaviorEntity == null) {
            throw new IllegalArgumentException("BehaviorEntity not found");
        }



            DailyNoteEntity dailyNoteEntity = DailyNoteEntity.builder()
                    .behaviorEntity(behaviorEntity)
                    .date(dailyNoteAddRequest.getDate())
                    .preSituation(dailyNoteAddRequest.getPreSituation())
                    .postSituation(dailyNoteAddRequest.getPostSituation())
                    .remark(dailyNoteAddRequest.getRemark())
                    .build();


            dailyNoteRepository.save(dailyNoteEntity);
            return true;

    }

    public DailyNoteEntity viewDailyNote(String behaviorId, LocalDate date) {
        BehaviorEntity behaviorEntity =
                customUserDetailsService
                        .userHasBehavior(customUserDetailsService.getCurrentUser(), Long.valueOf(behaviorId));
        if (behaviorEntity == null) {
            throw new IllegalArgumentException("BehaviorEntity not found");
        }

        return dailyNoteRepository.findByBehaviorEntityAndDate(behaviorEntity, date);



    }
}

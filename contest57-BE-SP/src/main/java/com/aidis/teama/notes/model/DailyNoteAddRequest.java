package com.aidis.teama.notes.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class DailyNoteAddRequest {

    private LocalDate date;
    private String preSituation;
    private String postSituation;
    private String remark;
}

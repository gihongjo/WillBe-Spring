package com.aidis.teama.student.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FirstStudentAddRequest {

    private String studentName;
    private LocalDate birthday;
    private String expressionLevel;
    private String studentStatus;
    private String behaviorType;
    private String behaviorName;
    private String recordType;
    private String behaviorStatus;
}

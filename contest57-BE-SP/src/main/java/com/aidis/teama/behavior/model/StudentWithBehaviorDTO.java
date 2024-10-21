package com.aidis.teama.behavior.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentWithBehaviorDTO {

    private Long studentId;
    private String studentName;
    private Long behaviorId;
    private String behaviorType;
    private String behaviorName;
    private String recordType;
    private String status;
    private String overDescription;
    private String overMeaning;
    private String overMeasures;

}

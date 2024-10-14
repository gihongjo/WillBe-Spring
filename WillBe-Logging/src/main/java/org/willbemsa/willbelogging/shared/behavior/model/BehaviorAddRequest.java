package org.willbemsa.willbelogging.shared.behavior.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BehaviorAddRequest {

    private Long studentId;
    private String behaviorType;
    private String behaviorName;
    private String recordType;
    private String status;


}

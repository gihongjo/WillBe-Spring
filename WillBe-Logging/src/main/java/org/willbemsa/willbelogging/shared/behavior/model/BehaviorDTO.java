package org.willbemsa.willbelogging.shared.behavior.model;

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
public class BehaviorDTO {

    private Long id;
    private String behaviorType;
    private String behaviorName;
    private String recordType;
    private String status;


}

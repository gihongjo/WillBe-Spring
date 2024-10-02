package com.aidis.teama.record.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class GraphDailyDTO {
    String behaviorId;
    String behaviorName;
    List<Integer> graph;

}

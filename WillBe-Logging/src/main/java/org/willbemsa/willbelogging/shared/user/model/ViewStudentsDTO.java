package org.willbemsa.willbelogging.shared.user.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class ViewStudentsDTO {

    private Long studentId;

    private Timestamp created_at;
    private String student_name;
    private LocalDate birthday;
    private String expressionLevel;
    private String status;

    List<ViewStudentBehaviorsDTO> viewStudentBehaviorsDTOList;
}

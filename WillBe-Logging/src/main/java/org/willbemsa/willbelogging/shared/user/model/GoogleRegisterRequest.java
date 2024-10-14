package org.willbemsa.willbelogging.shared.user.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleRegisterRequest {

    @NotNull
    private String userName;

    @NotNull
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotNull
    private String userId;
}

package org.willbemsa.willbelogging.shared.util.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String jwtToken;
}

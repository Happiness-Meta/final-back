package org.happinessmeta.last.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInResponse {
    public Long id;
    public String email;
    public String name;
    public String accessToken;
    public String refreshToken;
}

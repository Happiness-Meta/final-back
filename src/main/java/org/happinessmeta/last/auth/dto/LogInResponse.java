package org.happinessmeta.last.auth.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInResponse {
    public String token;
}

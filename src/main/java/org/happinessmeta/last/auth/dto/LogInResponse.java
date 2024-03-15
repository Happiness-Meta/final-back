package org.happinessmeta.last.auth.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInResponse {
    public Long id;
    public String email;
    public String name;
    public String token;
}

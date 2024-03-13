package org.happinessmeta.last.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.auth.domain.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private String position;
    private List<String> skills;
}

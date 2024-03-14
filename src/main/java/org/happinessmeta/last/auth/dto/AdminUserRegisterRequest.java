package org.happinessmeta.last.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.user.domain.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminUserRegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private List<Role> roles;
}

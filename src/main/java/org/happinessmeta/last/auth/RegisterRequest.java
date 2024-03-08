package org.happinessmeta.last.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.auth.user.Role;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private Role role;
}

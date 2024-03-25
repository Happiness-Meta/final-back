package org.happinessmeta.last.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.user.domain.Role;
import org.happinessmeta.last.user.domain.User;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private List<Role> roles;
    private List<String> techStack;
    private String position;
    private String telephone;
    private String address;

    private String industry;


    public static UserResponse convertUserToDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(), // 이 경우 이메일
                user.getName(),
                user.getRoles(),
                user.getTechStack(),
                user.getPosition(),
                user.getIndustry(),
                user.getTelephone(),
                user.getTelephone()
        );
    }
}

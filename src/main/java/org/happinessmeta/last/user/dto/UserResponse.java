package org.happinessmeta.last.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.user.domain.Role;
import org.happinessmeta.last.user.domain.User;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .roles(user.getRoles())
                .techStack(user.getTechStack())
                .position(user.getPosition())
                .telephone(user.getTelephone())
                .address(user.getAddress())
                .industry(user.getIndustry())
                .build();
    }
}

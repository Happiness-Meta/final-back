package org.happinessmeta.last.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("BASIC")
public class BasicUser extends User {
    @Column(nullable = false, unique = true)
    private String nickname;
    private String position;
    // 이 타입에 대한 테이블이 하나 만들어지고 1:N으로 맵핑
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> skills;

    @Builder
    public BasicUser(Long id,String email, String password, Role role, String nickname, String position, List<String> skills) {
        super(id, email, password, role);
        this.nickname = nickname;
        this.position = position;
        this.skills = skills;
    }
}
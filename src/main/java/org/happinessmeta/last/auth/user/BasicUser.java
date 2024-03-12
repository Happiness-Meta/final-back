package org.happinessmeta.last.auth.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("BASIC")
public class BasicUser extends User{
    @Column(nullable = false, unique = true)
    private String nickname;
    private String position;
    // 이 타입에 대한 테이블이 하나 만들어지고 1:N으로 맵핑
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> skills;
}
package org.happinessmeta.last.auth.user;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
//    // todo[질문] 백에서 enum 타입으로 가지고 있을 필요가 있나? 프론트에서 던지는 값 그대로 받으면 안되나. 하드 코딩 해야 하나?
//    private String position;
    // todo[질문] 백에서 enum 타입으로 가지고 있을 필요가 있나? 프론트에서 던지는 값 그대로 받으면 안되. 하드 코딩 해야 하나?
//    @ElementCollection(fetch = FetchType.LAZY)
//    private List<String> skills = new ArrayList<>();
    @Enumerated(value = EnumType.STRING)
    private Role role;

    // todo[확장]: 현재 역할을 하나만 주는 것으로 구현함.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    // 필드에 password를 추가한 다음에 @Data 를 붙이게 되면 이미 getter가 인식되어서 인터페이스 메서드 구체화 요청이 뜨질 않는다. 일관성을 위해서 넣어주자.
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


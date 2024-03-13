package org.happinessmeta.last.auth.domain;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
//todo: 생성자가 있어야 하는 이유에 대해서 고민해보기
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ROLE")
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable = false, unique = true)
    protected String email;
    protected String password;
    @Enumerated(value = EnumType.STRING)
    // role 필드를 자식 요소가 상속 받게 될 경우 이 컬럼이 중복 매핑되는 현상이 발생함
    @Column(insertable = false, updatable = false)
    protected Role role;

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


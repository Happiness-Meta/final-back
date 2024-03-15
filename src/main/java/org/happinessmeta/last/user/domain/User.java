package org.happinessmeta.last.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
//    @Column(name = "user_id")
=======
    @Column(name = "user_id")
>>>>>>> b0d55773dbfa57823a3a362242aef718505fb382
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Role> roles;
    // 기본 회원 고유 column
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> techStack;
    private String position;
    // 기업 회원 고유 column
    private String industry;

    @Builder
    public User(String email, String password, String name, List<Role> roles, List<String> techStack, String position, String industry) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles != null? new ArrayList<>(roles) : new ArrayList<>();
        this.techStack = techStack != null ? new ArrayList<>(techStack) : new ArrayList<>();
        this.position = position;
        this.industry = industry;
    }

    public void changeName(String name) {
        this.name = name;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void changePosition(String position) {
        this.position = position;
    }

    public void changeTechStack(List<String> techStack) {
        this.techStack = new ArrayList<>(techStack);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList();
//        return List.of(new SimpleGrantedAuthority(role.name()));
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


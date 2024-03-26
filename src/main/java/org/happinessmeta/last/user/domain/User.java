package org.happinessmeta.last.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.happinessmeta.last.payment.domain.Order;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.token.Token;
import org.happinessmeta.last.user.dto.UserUpdateRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles = new ArrayList<>();
    /*기본 회원 고유 column*/
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_tech_stack", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> techStack = new ArrayList<>();
    private String position;
    /*기업 회원 고유 column*/
    private String industry;
    // todo 주소의 경우 우편 번호와 실주소, 그리고 세부 주소가 있다. => embedded로 나타내는 방안 생각해보기(재활용성 고려)
    private String address;
    private String telephone;
    @OneToMany(mappedBy = "user")
    private List<PortfolioComponent> portfolioComponents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, List<Role> roles, List<String> techStack,String address, String telephone, String position, String industry) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
        this.techStack = techStack;
        this.position = position;
        this.address = address;
        this.telephone = telephone;
        this.industry = industry;
    }

    public void userInfoUpdate(UserUpdateRequest request) {
        this.password = request.getPassword();
        this.name = request.getName();
        this.techStack = request.getTechStack();
        this.position = request.getPosition();
        this.address = request.getAddress();
        this.telephone = request.getTelephone();
        this.industry = request.getIndustry();
    }
    // todo: 유저 정보 업데이트 방식 확정 지은 뒤 주석 삭제
//    public void changeName(String name) {
//        this.name = name;
//    }
//    public void changePassword(String password) {
//        this.password = password;
//    }
//    public void changePosition(String position) {
//        this.position = position;
//    }
//
//    public void changeTechStack(List<String> techStack) {
//        this.techStack = new ArrayList<>(techStack);
//    }

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


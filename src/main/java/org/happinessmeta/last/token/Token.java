package org.happinessmeta.last.token;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.user.domain.User;
// todo: 테이블 => redis 변환. 엔티티 제거 필요. refresh token만 redis에 잠깐 저장할 것. 영속성 부여할 필요 없음.
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}

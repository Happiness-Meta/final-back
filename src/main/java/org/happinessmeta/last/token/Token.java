package org.happinessmeta.last.token;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.user.domain.User;

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

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    //테이블 컬럼으로 들어가기 때문에 is- 패턴으로 작성하지 않음
    //토큰 이중 검증 위한 필드, 토큰 만료 여부(시간문제)와 토큰 권한 회수 여부
    private boolean expired;

    private boolean revoked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}

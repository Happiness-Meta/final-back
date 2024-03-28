package org.happinessmeta.last.token;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60*60*3) // 3시간 후에 데이터 삭제, 일단 리프레시 토큰 만료 기간과 동일하게 설정(초 단위)
public class RefreshToken {
    @Id
    private String refreshToken;
    private Long userId;
    // secondary index
    //    @Indexed

    public RefreshToken(String refreshToken, Long userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}

package org.happinessmeta.last.portfolio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.portfolio.utils.RedisComponent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTestService {
    private final RedisComponent redisComponent;

    public String getTest(){
        return redisComponent.getObjectByKey("test", String.class);
    }

    @Transactional
    public void create() {
        redisComponent.setObjectByKey("test", "Success Connection Test");
    }
}

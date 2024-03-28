package org.happinessmeta.last.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.portfolio.service.RedisTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/redis")
@RequiredArgsConstructor
public class RedisTestController {
    private final RedisTestService redisTestService;

    @GetMapping
    public String get(){
        return redisTestService.getTest();
    }

    @PostMapping
    public String create(){
        redisTestService.create();
        return "ok";
    }
}

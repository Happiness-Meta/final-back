package org.happinessmeta.last.user.controller;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    UserService service;
    // 회원 정보 불러오기
    @GetMapping("/user")
    public ResponseEntity<?> findUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.findUserByEmail(user.getEmail()));
    }
    @GetMapping("/users")
    public ResponseEntity<?> findAllUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.findAllUser());
    }
    //// 한명
    //// 전부
    // 회원 정보 수정
    // 회원 로그아웃
}

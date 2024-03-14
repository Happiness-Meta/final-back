package org.happinessmeta.last.auth.controller;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    // 회원가입
    // todo: 회원가입 시 발생 가능한 예외 구현하기
    @PostMapping("/basic/register")
    public ResponseEntity<AuthenticationResponse> registerBasicUser(
            @RequestBody BasicUserRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }
    // todo: 기업/어드민 사용자 회원가입 기능 구현
    @PostMapping("/company/register")
    public ResponseEntity<AuthenticationResponse> registerCompanyUser(
            @RequestBody CompanyUserRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponse> registerAdminUser(
            @RequestBody AdminUserRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }
    // todo: 사용자 불러오기 기능 구현
//    @GetMapping("user")
//    public ResponseEntity<BasicUser> findUser(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(service.findUser(user.getUsername()))
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}

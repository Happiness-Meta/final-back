package org.happinessmeta.last.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인/회원가입", description = "login/register api")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    // 회원가입
    // todo: 회원가입 시 발생 가능한 예외 구현하기
    @Operation(summary = "일반 사용자 회원가입", description = "")
    @PostMapping("/basic/register")
    public ResponseEntity<LogInResponse> registerBasicUser(
            @RequestBody BasicUserRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }
    // todo: 기업/어드민 사용자 회원가입 기능 구현
    @Operation(summary = "기업 사용자 회원가입", description = "")
    @PostMapping("/company/register")
    public ResponseEntity<LogInResponse> registerCompanyUser(
            @RequestBody CompanyUserRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }
    @Operation(summary = "어드민 사용자 회원가입", description = "")
    @PostMapping("/admin/register")
    public ResponseEntity<LogInResponse> registerAdminUser(
            @RequestBody AdminUserRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }
    // todo: 사용자 불러오기 기능 구현
//    @GetMapping("user")
//    public ResponseEntity<BasicUser> findUser(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(service.findUser(user.getUsername()))
//    }

    @Operation(summary = "로그인", description = "")
    @PostMapping("/login")
    public ResponseEntity<LogInResponse> register(
            @RequestBody LogInRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}

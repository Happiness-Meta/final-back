package org.happinessmeta.last.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.auth.service.AuthenticationService;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인/회원가입", description = "login/register api")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final ResponseService responseService;

    // 회원가입
    // todo: 회원가입 시 발생 가능한 예외 구현하기
    @Operation(summary = "일반 사용자 회원가입", description = "")
    @PostMapping("/basic/register")
    public ResponseEntity<SingleResult<RegisterResponse>> registerBasicUser(
            @RequestBody BasicUserRegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.registerUser(request),HttpStatus.OK.value()));
    }
    @Operation(summary = "기업 사용자 회원가입", description = "")
    @PostMapping("/company/register")
    public ResponseEntity<SingleResult<RegisterResponse>> registerCompanyUser(
            @RequestBody CompanyUserRegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.registerUser(request),HttpStatus.OK.value()));
    }
    @Operation(summary = "어드민 사용자 회원가입", description = "")
    @PostMapping("/admin/register")
    public ResponseEntity<SingleResult<RegisterResponse>> registerAdminUser(
            @RequestBody AdminUserRegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.registerUser(request),HttpStatus.OK.value()));
    }
    // todo: 사용자 불러오기 기능 구현
//    @GetMapping("user")
//    public ResponseEntity<BasicUser> findUser(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(service.findUser(user.getUsername()))
//    }
    @Operation(summary = "로그인", description = "")
    @PostMapping("/login")
    public ResponseEntity<SingleResult<LogInResponse>> login(
            @RequestBody LogInRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(responseService.handleSingleResult(service.logIn(request), HttpStatus.OK.value()));
    }
}

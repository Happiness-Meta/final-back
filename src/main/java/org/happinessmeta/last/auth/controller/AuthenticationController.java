package org.happinessmeta.last.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.auth.service.AuthenticationService;
import org.happinessmeta.last.common.exception.EmailPatternException;
import org.happinessmeta.last.common.exception.PasswordPatternException;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "로그인/회원가입", description = "login/register api")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final ResponseService responseService;

    /*회원가입*/
    @Operation(summary = "일반 사용자 회원가입", description = "")
    @PostMapping("/basic/register")
    public ResponseEntity<SingleResult<RegisterResponse>> registerBasicUser(
            @RequestBody @Validated BasicUserRegisterRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasFieldErrors("email")) throw new EmailPatternException();
        if (bindingResult.hasFieldErrors("password")) throw new PasswordPatternException();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.registerUser(request),HttpStatus.OK.value()));
    }
    @Operation(summary = "기업 사용자 회원가입", description = "")
    @PostMapping("/company/register")
    public ResponseEntity<SingleResult<RegisterResponse>> registerCompanyUser(
            @RequestBody @Validated CompanyUserRegisterRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasFieldErrors("email")) throw new EmailPatternException();
        if (bindingResult.hasFieldErrors("password")) throw new PasswordPatternException();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.registerUser(request), HttpStatus.OK.value()));
    }
    @Operation(summary = "어드민 사용자 회원가입", description = "")
    @PostMapping("/admin/register")
    public ResponseEntity<SingleResult<RegisterResponse>> registerAdminUser(
            @RequestBody @Validated AdminUserRegisterRequest request, BindingResult bindingResult
    ) {
        // todo: 어드민 회원도 회원 가입시 예외가 필요한지 고민하고 코드 수정하기
        if (bindingResult.hasFieldErrors("email")) throw new EmailPatternException();
        if (bindingResult.hasFieldErrors("password")) throw new PasswordPatternException();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.registerUser(request),HttpStatus.OK.value()));
    }

    /*로그인*/
    @Operation(summary = "로그인", description = "")
    @PostMapping("/login")
    public ResponseEntity<SingleResult<LogInResponse>> login(
            @RequestBody LogInRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(responseService.handleSingleResult(service.logIn(request), HttpStatus.OK.value()));
    }

    /*리프레시 토큰*/
    @Operation(summary = "리프레시 토큰", description = "")
    @PostMapping("/refresh-token")
//    public void ResponseEntity<SingleResult<LogInResponse>> refreshToken(
    public void refreshToken(
//            @RequestBody LogInRequest request
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response); //reponse 값 조작
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(responseService.handleSingleResult(response, HttpStatus.OK.value()));
    }
}

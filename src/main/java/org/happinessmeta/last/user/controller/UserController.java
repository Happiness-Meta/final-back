package org.happinessmeta.last.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.common.response.MultipleResult;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.SingleResult;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.dto.UserResponse;
import org.happinessmeta.last.user.dto.UserUpdate;
import org.happinessmeta.last.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 조회/수정/삭제", description = "")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final ResponseService responseService;

    /*회원 정보 불러오기*/
    @Operation(summary = "이메일로 회원 정보 불러오기", description = "")
    @GetMapping("/user")
    public ResponseEntity<SingleResult<UserResponse>> findUser(
            @AuthenticationPrincipal User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        responseService.handleSingleResult(
                                service.findUserByEmail(user.getUsername()),
                                HttpStatus.OK.value()
                        )
                );
    }
    // todo: 어드민 권한이 있는 경우만 모든 회원 정보 불러올 수 있도록 권한 관리 필요
    // 레포지토리에 있는 모든 회원 불러오기.
    @Operation(summary = "모든 회원 정보 불러오기", description = "")
    @GetMapping("/users")
    public ResponseEntity<MultipleResult<UserResponse>> findAllUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleListResult(service.findAllUser()));
    }

    @Operation(summary = "회원 정보 수정", description = "")
    @PutMapping("/user")
    public ResponseEntity<SingleResult<UserResponse>> updateUser(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody UserUpdate request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseService.handleSingleResult(service.updateUser(user.getUsername(), request), HttpStatus.OK.value()));
    }
    // 회원 로그아웃
}

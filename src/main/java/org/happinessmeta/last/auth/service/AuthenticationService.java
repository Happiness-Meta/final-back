package org.happinessmeta.last.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.token.Token;
import org.happinessmeta.last.token.TokenRepository;
import org.happinessmeta.last.token.TokenType;
import org.happinessmeta.last.user.domain.Role;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.common.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // basic user register
    @Transactional
    public RegisterResponse registerUser(BasicUserRegisterRequest request) {
        User user = User.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(Role.ROLE_BASIC))
                .position(request.getPosition())
                .techStack(request.getTechStack())
                .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .token(jwtToken)
                .build();
    }
    // company user register
    @Transactional
    public RegisterResponse registerUser(CompanyUserRegisterRequest request) {
        User user = User.builder()
                .name(request.getCompanyName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(Role.ROLE_COMPANY))
                .industry(request.getIndustry())
                .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(savedUser, jwtToken);
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .token(jwtToken)
                .build();
    }

    // admin user register
    @Transactional
    public RegisterResponse registerUser(AdminUserRegisterRequest request) {
        User user = User.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(Role.ROLE_ADMIN))
                .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(savedUser, jwtToken);
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .token(jwtToken)
                .build();
    }
    // todo: try catch가 아니라 security config 자체적으로 에러를 잡아주는 방법 고민해보기
    @Transactional
    public LogInResponse logIn(LogInRequest request) {
        try {
            log.info("request.getEmail: {}", request.getEmail());
            log.info("request.getPassword: {}", request.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException error) {
            log.info("인증 과정 중에 에러 발생 {}", error.getStackTrace());
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        log.info("user email: {}", user.getEmail());
        String jwtToken = jwtService.generateToken(user);
        // 로그인이 되면 기존에 있던 로그인 토큰은 만료됨
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return LogInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t ->{
            t.setRevoked(true);
            t.setExpired(true);
        });
        // update all the tokens
        tokenRepository.saveAll(validUserTokens);
    }
    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}

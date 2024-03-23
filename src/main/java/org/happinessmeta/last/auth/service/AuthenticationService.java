package org.happinessmeta.last.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.common.exception.ExistUserException;
import org.happinessmeta.last.common.exception.LoginFailureException;
import org.happinessmeta.last.common.exception.UserNameDuplicatedException;
import org.happinessmeta.last.token.Token;
import org.happinessmeta.last.token.TokenRepository;
import org.happinessmeta.last.token.TokenType;
import org.happinessmeta.last.user.domain.Role;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.common.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    // todo:코드 중복 제거하기
    /*basic user register*/
    @Transactional
    public RegisterResponse registerBasicUser(BasicUserRegisterRequest request) {
        validateDuplicatedUser(request.getEmail(), request.getNickname());
        User user = User.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(Role.ROLE_BASIC))
                .position(request.getPosition())
                .techStack(request.getTechStack())
                .build();
        userRepository.save(user);
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    /*company user register*/
    @Transactional
    public RegisterResponse registerUser(CompanyUserRegisterRequest request) {
        validateDuplicatedUser(request.getEmail(), request.getName());
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .address(request.getAddress())
                .industry(request.getIndustry())
                .roles(Collections.singletonList(Role.ROLE_COMPANY))
                .build();
        userRepository.save(user);
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    /*admin user register*/
    @Transactional
    public RegisterResponse registerUser(AdminUserRegisterRequest request) {
        validateDuplicatedUser(request.getEmail(), request.getNickname());
        User user = User.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(Role.ROLE_ADMIN))
                .build();
        userRepository.save(user);
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
    // todo: try catch가 아니라 security config 자체적으로 에러를 잡아주는 방법 고민해보기

    @Transactional
    public LogInResponse logIn(LogInRequest request) {
        try {
            log.info("로그인 시작");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException error) {
            log.info("로그인 인증 과정 중에 에러 발생 {}", error.getStackTrace());
        }
        // 실제로 계정이 있는지 확인
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailureException::new);
        // 비밀번호가 맞는지 확인-> raw password and encoded password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginFailureException();
        }
        log.info("user email: {}", user.getEmail());
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        // 로그인이 되면 기존에 있던 로그인 토큰은 만료됨
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        log.info("로그인 완료");
        return LogInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // springframework에서 제공하는 http를 사용해야 한다.
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return; // 뒷 작업으로 이어지지 않아야 한다.
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(UserNotFoundException::new);
            // todo: 리프레시 토큰 어디에 저장할 지 결정한 다음에 이 아래 코드 사용 여부 결정
//            boolean isTokenValid = tokenRepository.findByToken(refreshToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user); // 토큰 신규 발생
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                //todo: 리프레시 토큰과 로그인 dto 가 같으면 코드 해석 상 문제가 생기려나?
                LogInResponse authResponse = LogInResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
        ;
    }
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
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

    // todo: 사용자 별로 예외 메시지 다르게 보내느 방법 강구하기(현재, 회사/개인 사용자의 이름(회사이름)이 중복된다는 메시지 보내주고 있음)
    // 메서드 : 유저 = 1 : 1 관계
    private void validateDuplicatedUser(String email, String name) {
        if(userRepository.findByEmail(email).isPresent()) throw new ExistUserException();
        if(userRepository.findByName(name).isPresent()) throw new UserNameDuplicatedException();
    }
}

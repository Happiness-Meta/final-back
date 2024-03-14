package org.happinessmeta.last.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.common.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // 회원가입/로그인 시 token을 보내주는 것은 동일.
    // todo: builder 패턴의 문제?
    public LogInResponse registerUser(BasicUserRegisterRequest request) {
        User user = User.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .position(request.getPosition())
                .techStack(request.getTechStack())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return LogInResponse.builder()
                .token(jwtToken)
                .build();
    }
    public LogInResponse registerUser(CompanyUserRegisterRequest request) {
        User user = User.builder()
                .name(request.getCompanyName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .industry(request.getIndustry())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return LogInResponse.builder()
                .token(jwtToken)
                .build();
    }
    public LogInResponse registerUser(AdminUserRegisterRequest request) {
        User user = User.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return LogInResponse.builder()
                .token(jwtToken)
                .build();
    }
    // todo: 왜 여기서 try catch 문을 작성해야 하는지? 에러를 잡아주지 않으면 실행이 403이 뜨는 이유
    // todo: try catch가 아니라 security config 자체적으로 에러를 잡아주는 방법 고민해보기
    public LogInResponse authenticate(LogInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException error) {
            log.info("인증 과정 중에 에러 발생 {}", error.getStackTrace());
        }
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);

        return LogInResponse.builder()
                .token(jwtToken)
                .build();
    }
}

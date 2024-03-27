package org.happinessmeta.last.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.auth.dto.*;
import org.happinessmeta.last.common.exception.*;
import org.happinessmeta.last.token.RefreshToken;
import org.happinessmeta.last.token.RefreshTokenRepository;
import org.happinessmeta.last.token.Token;
//import org.happinessmeta.last.token.TokenRepository;
import org.happinessmeta.last.user.domain.Role;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.happinessmeta.last.common.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    // todo:코드 중복 제거하기
    /*basic user register*/
    @Transactional
    public RegisterResponse registerUser(BasicUserRegisterRequest request) {
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

    /*로그인*/
    @Transactional
    public LogInResponse logIn(LogInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailureException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))  throw new LoginFailureException();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        RefreshToken refreshTokenObj = new RefreshToken(refreshToken, user.getId());
        refreshTokenRepository.save(refreshTokenObj);
        return LogInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    /*리프레시 토큰*/
    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // springframework에서 제공하는 http를 사용해야 한다.
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(UserNotFoundException::new);
            // refreshTokenRepository에서 refreshtoken(type: string) 자체가 id로 지정되어 있음
            boolean isRefreshTokenObjPresent = refreshTokenRepository.findById(refreshToken).isPresent();
            if (jwtService.isTokenValid(refreshToken, user) && isRefreshTokenObjPresent) {
                String newAccessToken = jwtService.generateToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);
                RefreshToken newRefreshTokenObj = new RefreshToken(refreshToken, user.getId());
                refreshTokenRepository.save(newRefreshTokenObj);
                // 로그인 할 때와 동일한 응답 dto 사용
                LogInResponse authResponse = LogInResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    private void validateDuplicatedUser(String email, String name) {
        if(userRepository.findByEmail(email).isPresent()) throw new ExistUserException();
        if(userRepository.findByName(name).isPresent()) throw new UserNameDuplicatedException();
    }
//    private void revokeAllUserTokens(User user) {
//        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
//        if (validUserTokens.isEmpty()) {
//            return;
//        }
//        validUserTokens.forEach(t -> {
//            t.setRevoked(true);
//            t.setExpired(true);
//        });
//        tokenRepository.saveAll(validUserTokens);

//    }
//    private void saveUserToken(User user, String jwtToken) {
//        Token token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
    // 메서드 : 유저 = 1 : 1 관계
}

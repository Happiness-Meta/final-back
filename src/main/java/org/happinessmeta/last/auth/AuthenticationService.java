package org.happinessmeta.last.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.auth.user.User;
import org.happinessmeta.last.auth.user.UserRepository;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.common.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),
//                            request.getPassword()
//                    )
//            );
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception error) {
            System.out.println("________________________________");
            System.out.println(error.getStackTrace());
            System.out.println("________________________________");
            System.out.println(error.getMessage());
            System.out.println("________________________________");
            System.out.println(error.getMessage());
        }
        log.info("만약 여기까지 로그 안찍히면, authenticationManager 문제임");
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        log.info("user: {}", user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

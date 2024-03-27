package org.happinessmeta.last.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CorsConfigurationSource configurationSource;
//    private final LogoutHandler logoutHandler;
    private final AuthenticationEntryPoint jwtAuthEntryPoint;
    private final AccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer ->
                        corsCustomizer.configurationSource(configurationSource))
                .authorizeHttpRequests((authorize) ->
                                authorize
                                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                                        .requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/api/v1/resume/**").permitAll()
                                        .requestMatchers("/api/v1/order/**").permitAll()
                                        .requestMatchers("/api/v1/payment/**").permitAll()
                                        .anyRequest().authenticated()
//                                        .requestMatchers("/api/v1/portfolio/**").permitAll()
//                                        .requestMatchers("/api/v1/user", "/api/v1/users").permitAll()
//                                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManage ->
                        sessionManage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
//                )
                .exceptionHandling((handle) ->
                        handle.authenticationEntryPoint(jwtAuthEntryPoint) // 유저 정보 없이 접근한 경우
                                .accessDeniedHandler(jwtAccessDeniedHandler)) // 유저 정보는 있으나 자원에 접근 권한이 없는 경우
        ;
        return httpSecurity.build();
    }
}

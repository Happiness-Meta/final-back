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
import org.springframework.security.web.SecurityFilterChain;
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
    private final LogoutHandler logoutHandler;

//    private final JwtAuthEntryPoint jwtAuthEntryPoint;

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
                                        .requestMatchers("/api/v1/user", "/api/v1/users").permitAll()
//                                        .requestMatchers("/api/v1/demo-controller").permitAll()
                                        .requestMatchers("/api/v1/portfolio/**").permitAll()
                                        .requestMatchers("/api/v1/resume/**").permitAll()
                                        .requestMatchers("/api/v1/order/**").permitAll()
                                        .anyRequest().authenticated()
//                                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManage ->
                        sessionManage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                )
//                .exceptionHandling((handle) ->
//                        handle.authenticationEntryPoint(jwtAuthEntryPoint))
//                .securityContext((securityContext) -> securityContext
//                        .securityContextRepository(new RequestAttributeSecurityContextRepository()))
        ;
        return httpSecurity.build();
    }

    // todo[수정]: cors 문제 발생 가능 구역. 이 클래스에 있던 내용을 ApplicationConfig.java로 옮기고 빈 등록 후, 의존성 주입해줌.
//    @Bean
//    public CorsConfigurationSource configurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://processlogic.link/", "http://localhost:3000")); // 허용할 Origin
//        configuration.setAllowedMethods(Collections.singletonList("*")); // 허용할 HTTP Methods
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Collections.singletonList("*")); // 허용할 HTTP Headers
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
}

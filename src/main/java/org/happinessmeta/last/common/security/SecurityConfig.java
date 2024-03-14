package org.happinessmeta.last.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CorsConfigurationSource configurationSource;
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
                                        .requestMatchers("/api/auth/**").permitAll()
                                        .requestMatchers("/api/demo-controller").permitAll()
                                        .anyRequest().authenticated()
//                                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManage ->
                        sessionManage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
//                .exceptionHandling((handle) ->
//                        handle.authenticationEntryPoint(jwtAuthEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // todo[수정]: cors 문제 발생 가능 구역. 이 클래스에 있던 내용을 ApplicationConfig.java로 옮기고 빈 등록 후, 의존성 주입해줌.
//    @Bean
//    public CorsConfigurationSource configurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://processlogic.link/", "http://localhost:5173")); // 허용할 Origin
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

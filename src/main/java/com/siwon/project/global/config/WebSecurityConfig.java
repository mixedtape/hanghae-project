package com.siwon.project.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.siwon.project.domain.user.service.LogoutService;
import com.siwon.project.domain.user.UserDetailsService;
import com.siwon.project.global.jwt.JwtAuthorizationFilter;
import com.siwon.project.global.jwt.JwtUtil;
import com.siwon.project.global.redis.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final RedisUtil redisUtil;
    private final LogoutService logoutService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService, objectMapper);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 로컬 테스트, 프론트 배포 주소
        corsConfiguration.setAllowedOriginPatterns(
                Arrays.asList("http://localhost:8082", "https://hhive.store"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));

        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        corsConfiguration.setExposedHeaders(List.of("*"));

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll() // resource 접근 허용 설정
                        .requestMatchers("/api/users/**").permitAll() // '/api/users/'로 시작하는 요청 모두 접근 허가
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        http.logout(logoutConfig-> {
                    logoutConfig
                            .logoutUrl("/api/users/logout")
                            .addLogoutHandler(logoutService)
                            .logoutSuccessHandler((request, response, authentication) -> {
                                SecurityContextHolder.clearContext();
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.setContentType("application/json; charset=UTF-8");
                                response.getWriter().write("로그아웃 성공");
                            });
                });
        // 필터 처리
        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

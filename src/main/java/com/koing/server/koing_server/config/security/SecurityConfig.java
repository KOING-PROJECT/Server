package com.koing.server.koing_server.config.security;

import com.koing.server.koing_server.common.filter.JwtAuthenticationFilter;
import com.koing.server.koing_server.common.util.JwtTokenUtil;
import com.koing.server.koing_server.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// WebSecurityConfigurerAdapter 어떤 필터를 적용하여 필터체인을 구성할건지
//@Order(1) // 두개 이상의 security 필터를 사용할때 순서 정해주기
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtService jwtService;

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(List.of("https://koing-project.github.io", "*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));
//        configuration.setAllowCredentials(true);
//        configuration.setExposedHeaders(List.of("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .httpBasic().disable()
//                .cors().configurationSource(corsConfigurationSource())
//                .and()
                .csrf((csrfconfig) -> csrfconfig.disable())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests((requests) -> requests
//                        .antMatchers("/users", "/swagger-ui.html", "/sign-in", "/sign-up").permitAll()
                        .antMatchers("/swagger-ui.html", "/sign/**", "/mail/**").permitAll()
//                        .antMatchers("/jwt/reIssue").authenticated()
//                        .antMatchers("/user/**").authenticated()
                        .antMatchers("/user/**").permitAll()
                        .antMatchers("/guide/**").hasRole("GUIDE")
                        .antMatchers("/tourist/**").hasRole("TOURIST")
                )
                .formLogin().disable()
//                .cors().disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, jwtService)
                        , UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and().build();
//                .authenticationEntryPoint(((request, response, authException) -> {
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    objectMapper.writeValue(
//                            response.getOutputStream(),
//                            ExceptionResponse.of(ExceptionCode.FAIL_AUTHENTICATION)
//                    );
//                }))
//                .accessDeniedHandler(((request, response, accessDeniedException) -> {
//                    response.setStatus(HttpStatus.FORBIDDEN.value());
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    objectMapper.writeValue(
//                            response.getOutputStream(),
//                            ExceptionResponse.of(ExceptionCode.FAIL_AUTHORIZATION)
//                    );
//                })).and().build();
    }

}

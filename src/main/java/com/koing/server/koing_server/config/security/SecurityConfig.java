package com.koing.server.koing_server.config.security;

import com.koing.server.koing_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// WebSecurityConfigurerAdapter 어떤 필터를 적용하여 필터체인을 구성할건지
//@Order(1) // 두개 이상의 security 필터를 사용할때 순서 정해주기
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
//    private final JwtTokenProvider jwtTokenProvider;

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 disable
                .csrf().disable() // rest api 이므로 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // rest api 이므로 session 필요없음
                .and()
                    .authorizeRequests((requests) -> requests
                        .antMatchers("/**").permitAll()
                        .anyRequest().authenticated());
    }


}

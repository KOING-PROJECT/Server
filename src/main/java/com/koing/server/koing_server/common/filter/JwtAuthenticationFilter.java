package com.koing.server.koing_server.common.filter;

import com.koing.server.koing_server.common.model.JwtValidateEnum;
import com.koing.server.koing_server.common.util.JwtTokenUtil;
import com.koing.server.koing_server.domain.JwtToken.JwtToken;
import com.koing.server.koing_server.domain.JwtToken.repository.JwtTokenRepositoryImpl;
import com.koing.server.koing_server.service.JwtTokenService.JwtTokenService;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, JwtTokenService jwtTokenService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenUtil.getTokenInHeader((HttpServletRequest) servletRequest);

        LOGGER.info(String.format("[doFilter] token 값 추출 완료 token = %s", token));

        if(token != null && jwtTokenUtil.validationToken(token, (HttpServletRequest) servletRequest)) {
            // refreshToken이 탈취 되었을 경우 accessToken을 발급 받아서 요청을 보낼 수 있으므로
            // 만료 되지 않은 accessToken이라도 서버의 accessToken과 비교해봄

            LOGGER.info("[doFilter] Request의 Access token과 Server의 Access token 비교 시작");
            String userEmail = jwtTokenUtil.getUserEmailFromJwt(token);
            JwtToken serverJwtAccessToken = jwtTokenService.findJwtTokenByUserEmail(userEmail);

            if (!serverJwtAccessToken.equals(token)) {
                LOGGER.info("[doFilter] Request의 Access token과 Server의 Access token이 일치하지 않습니다.");
                servletRequest.setAttribute("JwtTokenException", JwtValidateEnum.NOT_MATCHED);
                throw new JwtException("Request의 Access token과 Server의 Access token이 일치하지 않습니다.");
            }
            LOGGER.info("[doFilter] Request의 Access token과 Server의 Access token 동일 확인");

            Authentication authentication = jwtTokenUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            LOGGER.info("[doFilter] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

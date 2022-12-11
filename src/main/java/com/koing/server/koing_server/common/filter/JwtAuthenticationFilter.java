package com.koing.server.koing_server.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.util.JwtTokenUtil;
import com.koing.server.koing_server.domain.jwt.JwtToken;
import com.koing.server.koing_server.service.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, JwtService jwtService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtService = jwtService;
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

            LOGGER.info("[doFilter] 받은 access token에서 email 추출 %s = " + userEmail);
            JwtToken serverJwtAccessToken = jwtService.findJwtTokenByUserEmail(userEmail);

            LOGGER.info("[doFilter] serverJwtAccessToken %s = " + serverJwtAccessToken);

            if (serverJwtAccessToken == null) {
                LOGGER.info("[doFilter] server에서 token을 찾을 수 없습니다. 회원가입을 먼저 진행해주세요.");
                setErrorResponse((HttpServletResponse) servletResponse, ErrorCode.NOT_FOUND_TOKEN_EXCEPTION);
                return;
            }

            if (!serverJwtAccessToken.getAccessToken().equals(token)) {
                LOGGER.info("[doFilter] Request의 Access token과 Server의 Access token이 일치하지 않습니다.");
                setErrorResponse((HttpServletResponse) servletResponse, ErrorCode.UNAUTHORIZED_TOKEN_NOT_MATCH_WITH_SERVER_EXCEPTION);
                return;
            }
            LOGGER.info("[doFilter] Request의 Access token과 Server의 Access token 동일 확인");

            Authentication authentication = jwtTokenUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            LOGGER.info("[doFilter] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorResponse errorResponse = ErrorResponse.error(errorCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

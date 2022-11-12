package com.koing.server.koing_server.common.filter;

import com.koing.server.koing_server.common.util.JwtTokenUtil;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenUtil.getTokenInHeader((HttpServletRequest) servletRequest);

        LOGGER.info(String.format("[doFilter] token 값 추출 완료 token = %s", token));

        if(token != null && jwtTokenUtil.validationToken(token)) {
            Authentication authentication = jwtTokenUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            LOGGER.info("[doFilter] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

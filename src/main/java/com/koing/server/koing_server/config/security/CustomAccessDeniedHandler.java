package com.koing.server.koing_server.config.security;

import com.koing.server.koing_server.common.error.ErrorStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        LOGGER.info("[handle] CustomAccessDeniedHandler 알 수 없는 권한이므로 접근이 불가능합니다.");

        response.setStatus(ErrorStatusCode.UNKNOWN_AUTHORITY.getStatus());
        response.sendRedirect("/sign-in"); // 추후 sign-in 하는 주소로 redirect해주기
    }
}

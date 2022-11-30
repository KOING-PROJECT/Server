package com.koing.server.koing_server.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.dto.EntryPointErrorResponse;
import com.koing.server.koing_server.common.exception.ErrorStatusCode;
import com.koing.server.koing_server.common.model.JwtValidateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("[commence] 인증 실패로 response.sendError 발생");

        Object jwtAttribute = request.getAttribute("JwtTokenException");

        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();

        setResponse(jwtAttribute, entryPointErrorResponse, response);

        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
    }

    private void setResponse(Object jwtAttribute, EntryPointErrorResponse entryPointErrorResponse, HttpServletResponse response) {
        if (jwtAttribute == null) {
            entryPointErrorResponse.setMsg("토큰이 없습니다.");

            response.setStatus(ErrorStatusCode.UNAUTHORIZED.getStatus());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            return;
        }

        String attribute = jwtAttribute.toString();

        if(attribute.equals(JwtValidateEnum.EXPIRE.getStatus())) {
            entryPointErrorResponse.setMsg("만료된 토큰입니다.");

            response.setStatus(ErrorStatusCode.EXPIRED_TOKEN.getStatus());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            return;
        }

        if(attribute.equals(JwtValidateEnum.DENIED.getStatus())) {
            entryPointErrorResponse.setMsg("잘못된 토큰 형식입니다.");

            response.setStatus(ErrorStatusCode.INVALID_FORMAT_TOKEN.getStatus());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            return;
        }

        if(attribute.equals(JwtValidateEnum.NOT_MATCHED.getStatus())) {
            entryPointErrorResponse.setMsg("Request의 Access token과 Server의 Access token이 일치하지 않습니다.");

            response.setStatus(ErrorStatusCode.NOT_MATCHED_ACCESS_TOKEN.getStatus());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            return;
        }

        entryPointErrorResponse.setMsg("예상치 못한 에러가 발생했습니다.");
        response.setStatus(500);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }
}

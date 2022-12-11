package com.koing.server.koing_server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.dto.EntryPointErrorResponse;
import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.error.ErrorStatusCode;
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

//        ErrorResponse errorResponse = new ErrorResponse();
//
//        setResponse(jwtAttribute, entryPointErrorResponse, response);
//
//        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
        setErrorResponse(jwtAttribute, response);
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

        entryPointErrorResponse.setMsg("예상치 못한 에러가 발생했습니다.");
        response.setStatus(500);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }

    private void setErrorResponse(
            Object jwtAttribute,
            HttpServletResponse response
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        if (jwtAttribute == null) {
            response.setStatus(ErrorCode.UNAUTHORIZED_EXCEPTION.getStatus());
            ErrorResponse errorResponse = ErrorResponse.error(ErrorCode.UNAUTHORIZED_EXCEPTION);
            try{
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }catch (IOException e){
                e.printStackTrace();
            }
            return;
        }

        String attribute = jwtAttribute.toString();

        if(attribute.equals(JwtValidateEnum.EXPIRE.getStatus())) {
            response.setStatus(ErrorCode.UNAUTHORIZED_EXPIRE_TOKEN_EXCEPTION.getStatus());
            ErrorResponse errorResponse = ErrorResponse.error(ErrorCode.UNAUTHORIZED_EXPIRE_TOKEN_EXCEPTION);
            try{
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }catch (IOException e){
                e.printStackTrace();
            }
            return;
        }

        if(attribute.equals(JwtValidateEnum.DENIED.getStatus())) {
            response.setStatus(ErrorCode.UNAUTHORIZED_INVALID_TOKEN_EXCEPTION.getStatus());
            ErrorResponse errorResponse = ErrorResponse.error(ErrorCode.UNAUTHORIZED_INVALID_TOKEN_EXCEPTION);
            try{
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }catch (IOException e){
                e.printStackTrace();
            }
            return;
        }

        response.setStatus(ErrorCode.INTERNAL_SERVER_EXCEPTION.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorResponse errorResponse = ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

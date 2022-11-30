package com.koing.server.koing_server.service.JwtTokenService;

import com.koing.server.koing_server.domain.JwtToken.JwtToken;
import com.koing.server.koing_server.domain.JwtToken.repository.JwtTokenRepositoryImpl;
import com.koing.server.koing_server.service.sign.SignService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtTokenService.class);

    private final JwtTokenRepositoryImpl jwtTokenRepositoryImpl;

    public JwtToken findJwtTokenByUserEmail(String userEmail) {
        JwtToken jwtToken = jwtTokenRepositoryImpl.findJwtTokenByUserEmail(userEmail);

        return jwtToken;
    }

}

package com.koing.server.koing_server.domain.Jwt.repository;

import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.domain.Jwt.JwtToken;

public interface JwtTokenRepositoryCustom {

    JwtToken findJwtTokenByUserEmail(String userEmail) throws NotFoundException;

}

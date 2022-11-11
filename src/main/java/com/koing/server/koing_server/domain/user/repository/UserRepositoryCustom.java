package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findAllUserByEnabled(boolean enabled);

}

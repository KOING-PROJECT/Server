package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findAllUserByEnabled(boolean enabled);

    User loadUserByUserEmail(String email, boolean enabled); // 추후에 UserNotFoundException 만들어서 throw 해주기

}

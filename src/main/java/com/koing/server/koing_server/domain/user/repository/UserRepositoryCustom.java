package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.domain.user.User;

import java.util.List;
import java.util.Set;

public interface UserRepositoryCustom {

    List<User> findAllUserByEnabled(boolean enabled) throws NotFoundException;

    User loadUserByUserEmail(String email, boolean enabled) throws NotFoundException;

    Boolean isExistUserByUserEmail(String email);

    Boolean isExistUserByUserId(Long userId);

    User loadUserByUserId(Long userId, boolean enabled);

    List<User> findAllGuideByEnabled(boolean enabled);

    List<User> findAllUsers();

    List<User> findWithdrawalUsers();

}

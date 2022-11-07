package com.koing.server.koing_server.controller.user.repository;

import com.koing.server.koing_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByEnabled(boolean enabled);

}

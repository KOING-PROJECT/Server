package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

}

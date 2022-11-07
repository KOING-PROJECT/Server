package com.koing.server.koing_server.service.user;

import com.koing.server.koing_server.controller.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<User> getUsers() {
        List<User> users = userRepository.findAllByEnabled(true);

        return users;
    }


}

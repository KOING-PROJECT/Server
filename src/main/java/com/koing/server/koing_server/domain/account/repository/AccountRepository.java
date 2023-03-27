package com.koing.server.koing_server.domain.account.repository;

import com.koing.server.koing_server.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

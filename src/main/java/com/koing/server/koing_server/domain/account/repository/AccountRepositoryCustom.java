package com.koing.server.koing_server.domain.account.repository;

import com.koing.server.koing_server.domain.account.Account;

public interface AccountRepositoryCustom {

    Account findAccountByUserId(Long userId);

}

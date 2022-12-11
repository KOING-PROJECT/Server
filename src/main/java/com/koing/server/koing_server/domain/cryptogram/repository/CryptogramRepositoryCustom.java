package com.koing.server.koing_server.domain.cryptogram.repository;

import com.koing.server.koing_server.domain.cryptogram.Cryptogram;

public interface CryptogramRepositoryCustom {

    boolean hasCryptogramByUserEmail(String userEmail);
    Cryptogram findCryptogramByUserEmail(String userEmail);

}

package com.koing.server.koing_server.domain.cryptogram.repository;

import com.koing.server.koing_server.domain.cryptogram.Cryptogram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptogramRepository extends JpaRepository<Cryptogram, Long> {
}

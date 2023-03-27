package com.koing.server.koing_server.domain.keyword.repository;

import com.koing.server.koing_server.domain.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}

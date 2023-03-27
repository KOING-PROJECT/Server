package com.koing.server.koing_server.domain.keyword.repository;

import com.koing.server.koing_server.domain.keyword.Keyword;

import java.util.List;

public interface KeywordRepositoryCustom {

    List<Keyword> findAllKeywords();

    Keyword findKeywordByKeyword(String keyword);

}

package com.koing.server.koing_server.service.keyword;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.keyword.Keyword;
import com.koing.server.koing_server.domain.keyword.repository.KeywordRepository;
import com.koing.server.koing_server.domain.keyword.repository.KeywordRepositoryCustom;
import com.koing.server.koing_server.domain.keyword.repository.KeywordRepositoryImpl;
import com.koing.server.koing_server.service.keyword.dto.KeywordCreateDto;
import com.koing.server.koing_server.service.keyword.dto.KeywordDeleteDto;
import com.koing.server.koing_server.service.keyword.dto.KeywordResponseDto;
import com.koing.server.koing_server.service.keyword.dto.KeywordResponseListDto;
import com.koing.server.koing_server.service.tour.TourService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourService.class);
    private final KeywordRepository keywordRepository;
    private final KeywordRepositoryImpl keywordRepositoryImpl;

    @Transactional
    public SuperResponse createKeywords(KeywordCreateDto keywordCreateDto) {
        LOGGER.info("[KeywordService] Keyword 생성 시도");

        for (String keyword : keywordCreateDto.getKeywords()) {
            if (keywordRepositoryImpl.findKeywordByKeyword(keyword) != null) {
                throw new NotAcceptableException("이미 존재하는 키워드 입니다.", ErrorCode.NOT_ACCEPTABLE_ALREADY_EXIST_KEYWORD_EXCEPTION);
            }

            Keyword createdKeyword = new Keyword(keyword);

            Keyword savedKeyword = keywordRepository.save(createdKeyword);

            if (savedKeyword == null) {
                throw new DBFailException("키워드 생성 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_KEYWORD_EXCEPTION);
            }
        }

        LOGGER.info("[KeywordService] Keyword 생성 성공");

        return SuccessResponse.success(SuccessCode.KEYWORD_CREATE_SUCCESS, null);
    }

    public SuperResponse getKeywords() {
        LOGGER.info("[KeywordService] Keyword 조회 시도");

        List<Keyword> keywords = keywordRepositoryImpl.findAllKeywords();

        List<KeywordResponseDto> keywordResponseDtos = new ArrayList<>();

        for (Keyword keyword : keywords) {
            keywordResponseDtos.add(new KeywordResponseDto(keyword));
        }

        LOGGER.info("[KeywordService] Keyword 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_KEYWORD_SUCCESS, new KeywordResponseListDto(keywordResponseDtos));
    }

    @Transactional
    public SuperResponse deleteKeywords(KeywordDeleteDto keywordDeleteDto) {
        LOGGER.info("[KeywordService] Keywords 삭제 시도");

        for (String keywordName : keywordDeleteDto.getDeleteKeywords()) {
            Keyword keyword = getKeyword(keywordName);

            keywordRepository.delete(keyword);
        }

        LOGGER.info("[KeywordService] Keywords 삭제 성공");

        return SuccessResponse.success(SuccessCode.DELETE_KEYWORD_SUCCESS, null);
    }

    public SuperResponse countKeywordSearchAmount(String keywordName) {
        LOGGER.info("[KeywordService] Keywords searchAmount 업데이트 시도");

        Keyword keyword = getKeyword(keywordName);

        int previousSearchAmount = keyword.getSearchAmount();
        keyword.setSearchAmount(previousSearchAmount + 1);

        Keyword savedKeyword = keywordRepository.save(keyword);

        if (savedKeyword.getSearchAmount() == previousSearchAmount) {
            throw new DBFailException("키워드 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_KEYWORD_EXCEPTION);
        }
        LOGGER.info("[KeywordService] Keywords searchAmount 업데이트 성공");

        return SuccessResponse.success(SuccessCode.KEYWORD_UPDATE_SUCCESS, null);
    }

    private Keyword getKeyword(String keywordName) {
        Keyword keyword = keywordRepositoryImpl.findKeywordByKeyword(keywordName);
        if (keyword == null) {
            throw new NotFoundException("존재하지 않는 키워드 입니다.", ErrorCode.NOT_FOUND_KEYWORD_EXCEPTION);
        }

        return keyword;
    }

}

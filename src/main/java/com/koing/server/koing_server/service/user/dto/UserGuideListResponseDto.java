package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserGuideListResponseDto {

    public UserGuideListResponseDto(List<UserGuideListDto> userGuideListDtos) {
        this.guides = userGuideListDtos;
    }

    private List<UserGuideListDto> guides;

}

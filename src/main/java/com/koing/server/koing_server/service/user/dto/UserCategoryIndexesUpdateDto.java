package com.koing.server.koing_server.service.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserCategoryIndexesUpdateDto {

    private Long userId;
    private Set<Integer> categoryIndexes;

}

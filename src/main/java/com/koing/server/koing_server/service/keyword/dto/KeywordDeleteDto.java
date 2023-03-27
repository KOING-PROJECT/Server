package com.koing.server.koing_server.service.keyword.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class KeywordDeleteDto {

    private List<String> deleteKeywords;

}

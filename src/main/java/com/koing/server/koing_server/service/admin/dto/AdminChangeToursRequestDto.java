package com.koing.server.koing_server.service.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminChangeToursRequestDto {

    private List<Long> tourIds;

}

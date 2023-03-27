package com.koing.server.koing_server.service.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserTourParticipantListDto {

    public UserTourParticipantListDto(List<UserTourParticipantDto> participants) {
        this.participants = participants;
    }

    private List<UserTourParticipantDto> participants;

}

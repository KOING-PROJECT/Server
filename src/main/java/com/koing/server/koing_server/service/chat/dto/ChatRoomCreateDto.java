package com.koing.server.koing_server.service.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ChatRoomCreateDto {

    private Long createUserId;
    private Set<Long> chattingUserIds;
    private Long relatedTourId;
    private String tourDate;
    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션

}

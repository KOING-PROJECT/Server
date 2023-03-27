package com.koing.server.koing_server.service.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessageSendDto {

    private String roomId;
    private Long writerId;
    private String writerName;
    private String message;

}

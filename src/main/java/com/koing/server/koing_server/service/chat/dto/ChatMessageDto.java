package com.koing.server.koing_server.service.chat.dto;

import com.koing.server.koing_server.domain.chat.ChatMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessageDto {

    public ChatMessageDto(ChatMessage chatMessage) {
        this.writerName = chatMessage.getWriter().getName();
        this.writerImageUrl = chatMessage.getWriter().getUserOptionalInfo().getImageUrl();
        this.message = chatMessage.getMessage();
        this.time = chatMessage.getCreatedAt();
    }

    private String writerName;
    private String writerImageUrl;
    private String message;
    private LocalDateTime time;

}

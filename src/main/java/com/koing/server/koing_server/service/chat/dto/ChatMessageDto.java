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
        if (chatMessage.getWriter().getUserOptionalInfo().getImageUrls() != null &&
                chatMessage.getWriter().getUserOptionalInfo().getImageUrls().size() > 0) {
            this.writerImageUrl = chatMessage.getWriter().getUserOptionalInfo().getImageUrls().get(0);
        }
        this.message = chatMessage.getMessage();
        this.time = chatMessage.getCreatedAt();
    }

    private String writerName;
    private String writerImageUrl;
    private String message;
    private LocalDateTime time;

}

package com.koing.server.koing_server.domain.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.MessageType;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.chat.dto.ChatMessageSendDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_MESSAGE_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatMessage extends AuditingTimeEntity {

    public ChatMessage(ChatRoom chatRoom, User writer, ChatMessageSendDto chatMessageSendDto, MessageType messageType) {
        this.chatRoom = chatRoom;
        this.writer = writer;
        this.message = chatMessageSendDto.getMessage();
        this.messageType = messageType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(length = 50)
//    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @OneToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;

        if (chatRoom.getMessages() == null) {
            chatRoom.setMessages(new ArrayList<>());
        }

        chatRoom.getMessages().add(this);
    }

}

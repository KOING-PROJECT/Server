package com.koing.server.koing_server.service.chat;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.MessageType;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.chat.ChatMessage;
import com.koing.server.koing_server.domain.chat.ChatRoom;
import com.koing.server.koing_server.domain.chat.repository.ChatMessageRepository;
import com.koing.server.koing_server.domain.chat.repository.ChatRoomRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.chat.dto.ChatMessageSendDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatMessageService.class);
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepositoryImpl chatRoomRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;

    public SuperResponse sendMessage(ChatMessageSendDto chatMessageSendDto, MessageType messageType) {
        LOGGER.info("[ChatMessageService] 보낸 메시지 저장 시도");

        ChatRoom chatRoom = chatRoomRepositoryImpl.findChatRoomByRoomId(chatMessageSendDto.getRoomId());
        if (chatRoom == null) {
            throw new NotFoundException("해당 채팅방을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_CHAT_ROOM_EXCEPTION);
        }
        LOGGER.info("[ChatMessageService] 해당 채팅방 조회 성공");

        User writer = userRepositoryImpl.loadUserByUserId(chatMessageSendDto.getWriterId(), true);
        if (writer == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
        LOGGER.info("[ChatMessageService] 해당 유저 조회 성공");

        ChatMessage chatMessage = new ChatMessage(chatRoom, writer, chatMessageSendDto, messageType);
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        if (savedChatMessage == null) {
            throw new DBFailException("메시지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_CHAT_MESSAGE_FAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.CHAT_MESSAGE_CREATE_SUCCESS, null);
    }

}

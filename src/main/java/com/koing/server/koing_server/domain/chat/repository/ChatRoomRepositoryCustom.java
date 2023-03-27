package com.koing.server.koing_server.domain.chat.repository;

import com.koing.server.koing_server.domain.chat.ChatRoom;

public interface ChatRoomRepositoryCustom {

    ChatRoom findChatRoomByRoomId(String roomId);

}

package com.koing.server.koing_server.domain.chat.repository;

import com.koing.server.koing_server.domain.chat.ChatRoom;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.chat.QChatMessage.chatMessage;
import static com.koing.server.koing_server.domain.chat.QChatRoom.chatRoom;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public ChatRoom findChatRoomByRoomId(String roomId) {
        return jpqlQueryFactory
                .selectFrom(chatRoom)
                .leftJoin(chatRoom.createUser, user)
                .fetchJoin()
                .leftJoin(chatRoom.chattingUsers, user)
                .fetchJoin()
                .leftJoin(chatRoom.relatedTourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(chatRoom.messages, chatMessage)
                .fetchJoin()
                .distinct()
                .where(
                        chatRoom.roomId.eq(roomId)
                )
                .fetchOne();
    }

}

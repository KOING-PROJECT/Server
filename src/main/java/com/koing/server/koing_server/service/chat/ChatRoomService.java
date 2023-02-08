package com.koing.server.koing_server.service.chat;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.chat.ChatMessage;
import com.koing.server.koing_server.domain.chat.ChatRoom;
import com.koing.server.koing_server.domain.chat.repository.ChatRoomRepository;
import com.koing.server.koing_server.domain.chat.repository.ChatRoomRepositoryImpl;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.chat.dto.ChatMessageDto;
import com.koing.server.koing_server.service.chat.dto.ChatRoomCreateDto;
import com.koing.server.koing_server.service.chat.dto.ChatRoomDetailDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatRoomService.class);
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRepositoryImpl chatRoomRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourApplicationRepositoryImpl tourApplicationRepositoryImpl;

//    @Transactional
//    public SuperResponse createChatRoom(ChatRoomCreateDto chatRoomCreateDto) {
//        LOGGER.info("[ChatRoomService] Chat room 생성 시도");
//
//        User createUser = getUser(chatRoomCreateDto.getCreateUserId());
//        LOGGER.info("[ChatRoomService] Chat room 생성 유저 조회 성공");
//
//        TourApplication relatedTourApplication = getTourApplication(
//                chatRoomCreateDto.getRelatedTourId(),
//                chatRoomCreateDto.getTourDate()
//        );
//
//        LOGGER.info("[ChatRoomService] Chat room 관련 투어 조회 성공");
//
//        Set<User> chattingUsers = new HashSet<>();
//        Set<String> chattingUserNames = new HashSet<>();
//
//        chattingUsers.add(createUser);
//        chattingUserNames.add(createUser.getName());
//
//        for (Long userId : chatRoomCreateDto.getChattingUserIds()) {
//            User chatUser = getUser(userId);
//            chattingUsers.add(chatUser);
//            chattingUserNames.add(chatUser.getName());
//        }
//
//        // 가이드가 생성한 chat이 아니면 guide도 chattingUsers에 추가
//        if (!createUser.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
//            User guide = relatedTourApplication.getTour().getCreateUser();
//            chattingUsers.add(guide);
//            chattingUserNames.add(guide.getName());
//        }
//
//        LOGGER.info("[ChatRoomService] Chat room 생성 유저들 조회 및 추가 성공");
//
//        ChatRoom chatRoom = new ChatRoom(chatRoomCreateDto);
//
//        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
//        if (savedChatRoom == null) {
//            throw new DBFailException("채팅방 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.",
//                    ErrorCode.DB_FAIL_CREATE_CHAT_ROOM_FAIL_EXCEPTION);
//        }
//
//        LOGGER.info("[ChatRoomService] Chat room 생성 성공");
//
//        return SuccessResponse.success(
//                SuccessCode.CHAT_ROOM_CREATE_SUCCESS,
//                new ChatRoomDetailDto(savedChatRoom.getRoomId(), relatedTourApplication, new ArrayList<>(chattingUserNames))
//        );
//    }
//
//    @Transactional
//    public SuperResponse getChatRoom(String roomId) {
//        LOGGER.info("[ChatRoomService] Chat room 조회 시도");
//
//        ChatRoom chatRoom = chatRoomRepositoryImpl.findChatRoomByRoomId(roomId);
//        if (chatRoom == null) {
//            throw new NotFoundException("해당 채팅방을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_CHAT_ROOM_EXCEPTION);
//        }
//
//        LOGGER.info("[ChatRoomService] Chat room 조회 성공");
//
//        List<String> chattingUserNames = new ArrayList<>();
//        for (User chattingUser : chatRoom.getChattingUsers()) {
//            chattingUserNames.add(chattingUser.getName());
//        }
//
//        List<ChatMessageDto> messageDtos = new ArrayList<>();
//        for (ChatMessage chatMessage : chatRoom.getMessages()) {
//            messageDtos.add(new ChatMessageDto(chatMessage));
//        }
//
//        return SuccessResponse.success(
//                SuccessCode.GET_CHAT_ROOM_SUCCESS,
//                new ChatRoomDetailDto(chatRoom.getRoomId(), chatRoom, chattingUserNames, messageDtos)
//        );
//    }

    private Tour getTour(Long tourId) {
        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);
        if (tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        return tour;
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private TourApplication getTourApplication(Long tourId, String tourDate) {
        TourApplication tourApplication = tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(tourId, tourDate);
        if (tourApplication == null) {
            throw new NotFoundException("해당 투어 신청서를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return tourApplication;
    }

}

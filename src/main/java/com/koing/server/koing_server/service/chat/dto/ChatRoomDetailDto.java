package com.koing.server.koing_server.service.chat.dto;

import com.koing.server.koing_server.domain.chat.ChatMessage;
import com.koing.server.koing_server.domain.chat.ChatRoom;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ChatRoomDetailDto {

    public ChatRoomDetailDto(String roomId, TourApplication tourApplication, List<String> chattingUserNames) {
        // chatRoom 생성 시 사용
        this.roomId = roomId;
        this.tourTitle = tourApplication.getTour().getTitle();
        this.currentTourParticipant = tourApplication.getCurrentParticipants();
        this.tourDate = tourApplication.getTourDate();
        this.chattingUserNames = chattingUserNames.stream().sorted().collect(Collectors.toList());
        this.messages = null;
    }

    public ChatRoomDetailDto(String roomId, ChatRoom chatRoom, List<String> chattingUserNames, List<ChatMessageDto> messages) {
        // chatRoom 조회 시 사용
//        this.roomId = roomId;
//        this.tourTitle = chatRoom.getRelatedTourApplication().getTour().getTitle();
//        this.currentTourParticipant = chatRoom.getRelatedTourApplication().getCurrentParticipants();
//        this.tourDate = chatRoom.getRelatedTourApplication().getTourDate();
//        this.chattingUserNames = chattingUserNames.stream().sorted().collect(Collectors.toList());
//        this.messages = messages;
    }

    private String roomId;
    private String tourTitle;
    private int currentTourParticipant;
    private String tourDate;
    private List<String> chattingUserNames;
    private List<ChatMessageDto> messages;

}

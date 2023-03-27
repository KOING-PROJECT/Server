package com.koing.server.koing_server.domain.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.chat.dto.ChatRoomCreateDto;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOM_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatRoom extends AuditingTimeEntity {
    // db에서 chatRoom의 chattingUserIds안에 로그인한 유저의 id가 있는지 확인하여 chatRoom 조회
    // roomUnuqueId는 chatting room을 구독할 때 사용
    // chatting list 가져올 때 relatedTourId와 tourDate로 tourApplication을 조회하고
    // 해당 tourApplication의 tourParticipant안에 해당 유저가 있는지 확인하여
    // 있으면 확정된 투어, 없으면 투어 문의로 리스트 보내주기

    public ChatRoom(ChatRoomCreateDto chatRoomCreateDto) {
        this.createUserId = chatRoomCreateDto.getCreateUserId();
        this.chattingUserIds = chatRoomCreateDto.getChattingUserIds();
        this.chatRoomTitle = chatRoomCreateDto.getRelatedTourTitle() + "/" + chatRoomCreateDto.getTourDate();
        this.tourDate = chatRoomCreateDto.getTourDate();
        this.relatedTourId = chatRoomCreateDto.getRelatedTourId();
        this.roomUniqueId = chatRoomCreateDto.getRelatedTourTitle() +
                chatRoomCreateDto.getTourDate() + UUID.randomUUID();
        this.messages = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long createUserId;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "chatting_user_table",
//            joinColumns = @JoinColumn(name = "chat_room_id"),
//            inverseJoinColumns = @JoinColumn(name = "chat_user_id"))
//    private Set<User> chattingUsers;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "chatting_user_id")
    private Set<Long> chattingUserIds;

    @Column(length = 200) // chatRoomTile = tourTitle + "/" + tourDate
    private String chatRoomTitle;

    @Column(length = 20)
    private String tourDate;

    private Long relatedTourId;

    @Column(length = 300)
    private String roomUniqueId; // roomUniqueId = tourTitle + tourDate + UUID

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatMessage> messages;

//    @ElementCollection(fetch = FetchType.LAZY)
//    @Column(length = 50, nullable = false, name = "sessions")
//    private Set<Long> participateUserIds;

}

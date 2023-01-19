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

    public ChatRoom(ChatRoomCreateDto chatRoomCreateDto, Set<User> chattingUsers, User createUser, TourApplication relatedTourApplication) {
        this.createUser = createUser;
        this.chattingUsers = chattingUsers;
        this.relatedTourApplication = relatedTourApplication;
        this.roomId = UUID.randomUUID().toString();
        this.name = chatRoomCreateDto.getName();
        this.messages = new ArrayList<>();
//        this.participateUserIds = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "chatting_user_table",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_user_id"))
    private Set<User> chattingUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    private TourApplication relatedTourApplication;

    @Column(length = 50)
    private String roomId;

    @Column(length = 20)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatMessage> messages;

//    @ElementCollection(fetch = FetchType.LAZY)
//    @Column(length = 50, nullable = false, name = "sessions")
//    private Set<Long> participateUserIds;

}

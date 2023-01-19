package com.koing.server.koing_server.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = 1098751931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final SetPath<com.koing.server.koing_server.domain.user.User, com.koing.server.koing_server.domain.user.QUser> chattingUsers = this.<com.koing.server.koing_server.domain.user.User, com.koing.server.koing_server.domain.user.QUser>createSet("chattingUsers", com.koing.server.koing_server.domain.user.User.class, com.koing.server.koing_server.domain.user.QUser.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.koing.server.koing_server.domain.user.QUser createUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ChatMessage, QChatMessage> messages = this.<ChatMessage, QChatMessage>createList("messages", ChatMessage.class, QChatMessage.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final com.koing.server.koing_server.domain.tour.QTourApplication relatedTourApplication;

    public final StringPath roomId = createString("roomId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createUser = inits.isInitialized("createUser") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("createUser"), inits.get("createUser")) : null;
        this.relatedTourApplication = inits.isInitialized("relatedTourApplication") ? new com.koing.server.koing_server.domain.tour.QTourApplication(forProperty("relatedTourApplication"), inits.get("relatedTourApplication")) : null;
    }

}


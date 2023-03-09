package com.koing.server.koing_server.domain.fcm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFCMToken is a Querydsl query type for FCMToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFCMToken extends EntityPathBase<FCMToken> {

    private static final long serialVersionUID = 1567811029L;

    public static final QFCMToken fCMToken = new QFCMToken("fCMToken");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFCMToken(String variable) {
        super(FCMToken.class, forVariable(variable));
    }

    public QFCMToken(Path<? extends FCMToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFCMToken(PathMetadata metadata) {
        super(FCMToken.class, metadata);
    }

}


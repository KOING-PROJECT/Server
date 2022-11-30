package com.koing.server.koing_server.domain.JwtToken;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJwtToken is a Querydsl query type for JwtToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJwtToken extends EntityPathBase<JwtToken> {

    private static final long serialVersionUID = 491227872L;

    public static final QJwtToken jwtToken = new QJwtToken("jwtToken");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final StringPath accessToken = createString("accessToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath userEmail = createString("userEmail");

    public QJwtToken(String variable) {
        super(JwtToken.class, forVariable(variable));
    }

    public QJwtToken(Path<? extends JwtToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJwtToken(PathMetadata metadata) {
        super(JwtToken.class, metadata);
    }

}


package com.koing.server.koing_server.domain.sms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSMS is a Querydsl query type for SMS
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSMS extends EntityPathBase<SMS> {

    private static final long serialVersionUID = -767783402L;

    public static final QSMS sMS = new QSMS("sMS");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final StringPath certificationNumber = createString("certificationNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath targetPhoneNumber = createString("targetPhoneNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final BooleanPath verified = createBoolean("verified");

    public QSMS(String variable) {
        super(SMS.class, forVariable(variable));
    }

    public QSMS(Path<? extends SMS> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSMS(PathMetadata metadata) {
        super(SMS.class, metadata);
    }

}


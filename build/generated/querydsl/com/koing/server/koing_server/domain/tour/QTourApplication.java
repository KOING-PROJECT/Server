package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourApplication is a Querydsl query type for TourApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourApplication extends EntityPathBase<TourApplication> {

    private static final long serialVersionUID = 1436224112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTourApplication tourApplication = new QTourApplication("tourApplication");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTour tour;

    public final EnumPath<com.koing.server.koing_server.common.enums.TourStatus> tourStatus = createEnum("tourStatus", com.koing.server.koing_server.common.enums.TourStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<com.koing.server.koing_server.domain.user.User, com.koing.server.koing_server.domain.user.QUser> users = this.<com.koing.server.koing_server.domain.user.User, com.koing.server.koing_server.domain.user.QUser>createList("users", com.koing.server.koing_server.domain.user.User.class, com.koing.server.koing_server.domain.user.QUser.class, PathInits.DIRECT2);

    public QTourApplication(String variable) {
        this(TourApplication.class, forVariable(variable), INITS);
    }

    public QTourApplication(Path<? extends TourApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTourApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTourApplication(PathMetadata metadata, PathInits inits) {
        this(TourApplication.class, metadata, inits);
    }

    public QTourApplication(Class<? extends TourApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tour = inits.isInitialized("tour") ? new QTour(forProperty("tour"), inits.get("tour")) : null;
    }

}


package com.koing.server.koing_server.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1519278752L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Integer> attachment = createNumber("attachment", Integer.class);

    public final StringPath birthDate = createString("birthDate");

    public final StringPath country = createString("country");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SetPath<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour> createTours = this.<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour>createSet("createTours", com.koing.server.koing_server.domain.tour.Tour.class, com.koing.server.koing_server.domain.tour.QTour.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final SetPath<User, QUser> follower = this.<User, QUser>createSet("follower", User.class, QUser.class, PathInits.DIRECT2);

    public final SetPath<User, QUser> following = this.<User, QUser>createSet("following", User.class, QUser.class, PathInits.DIRECT2);

    public final EnumPath<GenderType> gender = createEnum("gender", GenderType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final SetPath<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour> pressLikeTours = this.<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour>createSet("pressLikeTours", com.koing.server.koing_server.domain.tour.Tour.class, com.koing.server.koing_server.domain.tour.QTour.class, PathInits.DIRECT2);

    public final SetPath<String, StringPath> roles = this.<String, StringPath>createSet("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final SetPath<com.koing.server.koing_server.domain.tour.TourParticipant, com.koing.server.koing_server.domain.tour.QTourParticipant> tourParticipants = this.<com.koing.server.koing_server.domain.tour.TourParticipant, com.koing.server.koing_server.domain.tour.QTourParticipant>createSet("tourParticipants", com.koing.server.koing_server.domain.tour.TourParticipant.class, com.koing.server.koing_server.domain.tour.QTourParticipant.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserOptionalInfo userOptionalInfo;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userOptionalInfo = inits.isInitialized("userOptionalInfo") ? new QUserOptionalInfo(forProperty("userOptionalInfo")) : null;
    }

}


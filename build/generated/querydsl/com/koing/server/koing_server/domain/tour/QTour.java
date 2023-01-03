package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTour is a Querydsl query type for Tour
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTour extends EntityPathBase<Tour> {

    private static final long serialVersionUID = 1965583040L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTour tour = new QTour("tour");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final SetPath<java.util.Map<String, java.util.List<?>>, SimplePath<java.util.Map<String, java.util.List<?>>>> additionalPrice = this.<java.util.Map<String, java.util.List<?>>, SimplePath<java.util.Map<String, java.util.List<?>>>>createSet("additionalPrice", java.util.Map.class, SimplePath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.koing.server.koing_server.common.enums.CreateStatus> createStatus = createEnum("createStatus", com.koing.server.koing_server.common.enums.CreateStatus.class);

    public final com.koing.server.koing_server.domain.user.QUser createUser;

    public final StringPath description = createString("description");

    public final BooleanPath hasLevy = createBoolean("hasLevy");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> participant = createNumber("participant", Integer.class);

    public final SetPath<com.koing.server.koing_server.domain.user.User, com.koing.server.koing_server.domain.user.QUser> pressLikeUsers = this.<com.koing.server.koing_server.domain.user.User, com.koing.server.koing_server.domain.user.QUser>createSet("pressLikeUsers", com.koing.server.koing_server.domain.user.User.class, com.koing.server.koing_server.domain.user.QUser.class, PathInits.DIRECT2);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public final SetPath<TourApplication, QTourApplication> tourApplications = this.<TourApplication, QTourApplication>createSet("tourApplications", TourApplication.class, QTourApplication.class, PathInits.DIRECT2);

    public final SetPath<TourCategory, QTourCategory> tourCategories = this.<TourCategory, QTourCategory>createSet("tourCategories", TourCategory.class, QTourCategory.class, PathInits.DIRECT2);

    public final NumberPath<Integer> tourPrice = createNumber("tourPrice", Integer.class);

    public final QTourSchedule tourSchedule;

    public final EnumPath<com.koing.server.koing_server.common.enums.TourStatus> tourStatus = createEnum("tourStatus", com.koing.server.koing_server.common.enums.TourStatus.class);

    public final QTourSurvey tourSurvey;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTour(String variable) {
        this(Tour.class, forVariable(variable), INITS);
    }

    public QTour(Path<? extends Tour> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTour(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTour(PathMetadata metadata, PathInits inits) {
        this(Tour.class, metadata, inits);
    }

    public QTour(Class<? extends Tour> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createUser = inits.isInitialized("createUser") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("createUser"), inits.get("createUser")) : null;
        this.tourSchedule = inits.isInitialized("tourSchedule") ? new QTourSchedule(forProperty("tourSchedule"), inits.get("tourSchedule")) : null;
        this.tourSurvey = inits.isInitialized("tourSurvey") ? new QTourSurvey(forProperty("tourSurvey")) : null;
    }

}


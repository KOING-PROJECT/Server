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

    public final NumberPath<Integer> currentParticipants = createNumber("currentParticipants", Integer.class);

    public final EnumPath<com.koing.server.koing_server.common.enums.ProgressStatus> guideProgressStatus = createEnum("guideProgressStatus", com.koing.server.koing_server.common.enums.ProgressStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isExceed = createBoolean("isExceed");

    public final NumberPath<Integer> maxParticipant = createNumber("maxParticipant", Integer.class);

    public final SetPath<Long, NumberPath<Long>> reviewedTouristId = this.<Long, NumberPath<Long>>createSet("reviewedTouristId", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final SetPath<com.koing.server.koing_server.domain.review.ReviewToGuide, com.koing.server.koing_server.domain.review.QReviewToGuide> reviewsToGuide = this.<com.koing.server.koing_server.domain.review.ReviewToGuide, com.koing.server.koing_server.domain.review.QReviewToGuide>createSet("reviewsToGuide", com.koing.server.koing_server.domain.review.ReviewToGuide.class, com.koing.server.koing_server.domain.review.QReviewToGuide.class, PathInits.DIRECT2);

    public final QTour tour;

    public final EnumPath<com.koing.server.koing_server.common.enums.TourApplicationStatus> tourApplicationStatus = createEnum("tourApplicationStatus", com.koing.server.koing_server.common.enums.TourApplicationStatus.class);

    public final StringPath tourDate = createString("tourDate");

    public final ListPath<TourParticipant, QTourParticipant> tourParticipants = this.<TourParticipant, QTourParticipant>createList("tourParticipants", TourParticipant.class, QTourParticipant.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

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


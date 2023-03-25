package com.koing.server.koing_server.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewToGuide is a Querydsl query type for ReviewToGuide
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewToGuide extends EntityPathBase<ReviewToGuide> {

    private static final long serialVersionUID = -878983967L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewToGuide reviewToGuide = new QReviewToGuide("reviewToGuide");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final NumberPath<Double> attachment = createNumber("attachment", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SetPath<String, StringPath> guideReviews = this.<String, StringPath>createSet("guideReviews", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.koing.server.koing_server.domain.tour.QTourApplication relatedTourApplication;

    public final ListPath<String, StringPath> reviewPhotos = this.<String, StringPath>createList("reviewPhotos", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath totalReview = createString("totalReview");

    public final SetPath<String, StringPath> tourReviews = this.<String, StringPath>createSet("tourReviews", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.koing.server.koing_server.domain.user.QUser writeTourist;

    public QReviewToGuide(String variable) {
        this(ReviewToGuide.class, forVariable(variable), INITS);
    }

    public QReviewToGuide(Path<? extends ReviewToGuide> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewToGuide(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewToGuide(PathMetadata metadata, PathInits inits) {
        this(ReviewToGuide.class, metadata, inits);
    }

    public QReviewToGuide(Class<? extends ReviewToGuide> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.relatedTourApplication = inits.isInitialized("relatedTourApplication") ? new com.koing.server.koing_server.domain.tour.QTourApplication(forProperty("relatedTourApplication"), inits.get("relatedTourApplication")) : null;
        this.writeTourist = inits.isInitialized("writeTourist") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("writeTourist"), inits.get("writeTourist")) : null;
    }

}


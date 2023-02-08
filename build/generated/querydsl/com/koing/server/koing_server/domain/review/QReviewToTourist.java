package com.koing.server.koing_server.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewToTourist is a Querydsl query type for ReviewToTourist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewToTourist extends EntityPathBase<ReviewToTourist> {

    private static final long serialVersionUID = -102657065L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewToTourist reviewToTourist = new QReviewToTourist("reviewToTourist");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final NumberPath<Integer> attachment = createNumber("attachment", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<String, StringPath> progressReviews = this.<String, StringPath>createSet("progressReviews", String.class, StringPath.class, PathInits.DIRECT2);

    public final com.koing.server.koing_server.domain.tour.QTourParticipant relatedTourParticipant;

    public final SetPath<String, StringPath> touristReviews = this.<String, StringPath>createSet("touristReviews", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.koing.server.koing_server.domain.user.QUser writeGuide;

    public QReviewToTourist(String variable) {
        this(ReviewToTourist.class, forVariable(variable), INITS);
    }

    public QReviewToTourist(Path<? extends ReviewToTourist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewToTourist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewToTourist(PathMetadata metadata, PathInits inits) {
        this(ReviewToTourist.class, metadata, inits);
    }

    public QReviewToTourist(Class<? extends ReviewToTourist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.relatedTourParticipant = inits.isInitialized("relatedTourParticipant") ? new com.koing.server.koing_server.domain.tour.QTourParticipant(forProperty("relatedTourParticipant"), inits.get("relatedTourParticipant")) : null;
        this.writeGuide = inits.isInitialized("writeGuide") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("writeGuide"), inits.get("writeGuide")) : null;
    }

}


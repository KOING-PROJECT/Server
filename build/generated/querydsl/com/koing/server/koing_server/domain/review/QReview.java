package com.koing.server.koing_server.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -1180918720L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final NumberPath<Integer> attachment = createNumber("attachment", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SetPath<String, StringPath> guideReviews = this.<String, StringPath>createSet("guideReviews", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<String, StringPath> photos = this.<String, StringPath>createSet("photos", String.class, StringPath.class, PathInits.DIRECT2);

    public final com.koing.server.koing_server.domain.user.QUser receiveUser;

    public final com.koing.server.koing_server.domain.tour.QTour relatedTour;

    public final com.koing.server.koing_server.domain.user.QUser sendUser;

    public final StringPath totalReview = createString("totalReview");

    public final SetPath<String, StringPath> tourReviews = this.<String, StringPath>createSet("tourReviews", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiveUser = inits.isInitialized("receiveUser") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("receiveUser"), inits.get("receiveUser")) : null;
        this.relatedTour = inits.isInitialized("relatedTour") ? new com.koing.server.koing_server.domain.tour.QTour(forProperty("relatedTour"), inits.get("relatedTour")) : null;
        this.sendUser = inits.isInitialized("sendUser") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("sendUser"), inits.get("sendUser")) : null;
    }

}


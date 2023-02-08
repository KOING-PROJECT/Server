package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourParticipant is a Querydsl query type for TourParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourParticipant extends EntityPathBase<TourParticipant> {

    private static final long serialVersionUID = 649393235L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTourParticipant tourParticipant = new QTourParticipant("tourParticipant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> numberOfParticipants = createNumber("numberOfParticipants", Integer.class);

    public final com.koing.server.koing_server.domain.user.QUser participant;

    public final com.koing.server.koing_server.domain.review.QReviewToTourist reviewToTourist;

    public final QTourApplication tourApplication;

    public QTourParticipant(String variable) {
        this(TourParticipant.class, forVariable(variable), INITS);
    }

    public QTourParticipant(Path<? extends TourParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTourParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTourParticipant(PathMetadata metadata, PathInits inits) {
        this(TourParticipant.class, metadata, inits);
    }

    public QTourParticipant(Class<? extends TourParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.participant = inits.isInitialized("participant") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("participant"), inits.get("participant")) : null;
        this.reviewToTourist = inits.isInitialized("reviewToTourist") ? new com.koing.server.koing_server.domain.review.QReviewToTourist(forProperty("reviewToTourist"), inits.get("reviewToTourist")) : null;
        this.tourApplication = inits.isInitialized("tourApplication") ? new QTourApplication(forProperty("tourApplication"), inits.get("tourApplication")) : null;
    }

}


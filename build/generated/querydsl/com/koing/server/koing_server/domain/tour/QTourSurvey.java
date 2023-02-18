package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourSurvey is a Querydsl query type for TourSurvey
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourSurvey extends EntityPathBase<TourSurvey> {

    private static final long serialVersionUID = -1935282630L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTourSurvey tourSurvey = new QTourSurvey("tourSurvey");

    public final EnumPath<com.koing.server.koing_server.common.enums.CreateStatus> createStatus = createEnum("createStatus", com.koing.server.koing_server.common.enums.CreateStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath movingSupport = createBoolean("movingSupport");

    public final StringPath style = createString("style");

    public final QTour tour;

    public final StringPath type = createString("type");

    public QTourSurvey(String variable) {
        this(TourSurvey.class, forVariable(variable), INITS);
    }

    public QTourSurvey(Path<? extends TourSurvey> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTourSurvey(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTourSurvey(PathMetadata metadata, PathInits inits) {
        this(TourSurvey.class, metadata, inits);
    }

    public QTourSurvey(Class<? extends TourSurvey> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tour = inits.isInitialized("tour") ? new QTour(forProperty("tour"), inits.get("tour")) : null;
    }

}


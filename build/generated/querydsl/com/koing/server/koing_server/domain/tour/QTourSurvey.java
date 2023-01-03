package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTourSurvey is a Querydsl query type for TourSurvey
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourSurvey extends EntityPathBase<TourSurvey> {

    private static final long serialVersionUID = -1935282630L;

    public static final QTourSurvey tourSurvey = new QTourSurvey("tourSurvey");

    public final EnumPath<com.koing.server.koing_server.common.enums.CreateStatus> createStatus = createEnum("createStatus", com.koing.server.koing_server.common.enums.CreateStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath movingSupport = createBoolean("movingSupport");

    public final StringPath style = createString("style");

    public final StringPath type = createString("type");

    public QTourSurvey(String variable) {
        super(TourSurvey.class, forVariable(variable));
    }

    public QTourSurvey(Path<? extends TourSurvey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourSurvey(PathMetadata metadata) {
        super(TourSurvey.class, metadata);
    }

}


package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourSchedule is a Querydsl query type for TourSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourSchedule extends EntityPathBase<TourSchedule> {

    private static final long serialVersionUID = 817013111L;

    public static final QTourSchedule tourSchedule = new QTourSchedule("tourSchedule");

    public final BooleanPath dateNegotiation = createBoolean("dateNegotiation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tourDate = createString("tourDate");

    public final ListPath<TourDetailSchedule, QTourDetailSchedule> tourDetailScheduleList = this.<TourDetailSchedule, QTourDetailSchedule>createList("tourDetailScheduleList", TourDetailSchedule.class, QTourDetailSchedule.class, PathInits.DIRECT2);

    public QTourSchedule(String variable) {
        super(TourSchedule.class, forVariable(variable));
    }

    public QTourSchedule(Path<? extends TourSchedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourSchedule(PathMetadata metadata) {
        super(TourSchedule.class, metadata);
    }

}


package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTourDetailSchedule is a Querydsl query type for TourDetailSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourDetailSchedule extends EntityPathBase<TourDetailSchedule> {

    private static final long serialVersionUID = 649565416L;

    public static final QTourDetailSchedule tourDetailSchedule = new QTourDetailSchedule("tourDetailSchedule");

    public final StringPath endTime = createString("endTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> latitude = createNumber("latitude", Long.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Long> longitude = createNumber("longitude", Long.class);

    public final StringPath startTime = createString("startTime");

    public QTourDetailSchedule(String variable) {
        super(TourDetailSchedule.class, forVariable(variable));
    }

    public QTourDetailSchedule(Path<? extends TourDetailSchedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourDetailSchedule(PathMetadata metadata) {
        super(TourDetailSchedule.class, metadata);
    }

}


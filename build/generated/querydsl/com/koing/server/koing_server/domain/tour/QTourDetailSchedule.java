package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourDetailSchedule is a Querydsl query type for TourDetailSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourDetailSchedule extends EntityPathBase<TourDetailSchedule> {

    private static final long serialVersionUID = 649565416L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTourDetailSchedule tourDetailSchedule = new QTourDetailSchedule("tourDetailSchedule");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath endTime = createString("endTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath startTime = createString("startTime");

    public final QTourSchedule tourSchedule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTourDetailSchedule(String variable) {
        this(TourDetailSchedule.class, forVariable(variable), INITS);
    }

    public QTourDetailSchedule(Path<? extends TourDetailSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTourDetailSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTourDetailSchedule(PathMetadata metadata, PathInits inits) {
        this(TourDetailSchedule.class, metadata, inits);
    }

    public QTourDetailSchedule(Class<? extends TourDetailSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tourSchedule = inits.isInitialized("tourSchedule") ? new QTourSchedule(forProperty("tourSchedule"), inits.get("tourSchedule")) : null;
    }

}


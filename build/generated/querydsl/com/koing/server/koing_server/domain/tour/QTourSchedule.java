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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTourSchedule tourSchedule = new QTourSchedule("tourSchedule");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.koing.server.koing_server.common.enums.CreateStatus> createStatus = createEnum("createStatus", com.koing.server.koing_server.common.enums.CreateStatus.class);

    public final BooleanPath dateNegotiation = createBoolean("dateNegotiation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTour tour;

    public final SetPath<String, StringPath> tourDates = this.<String, StringPath>createSet("tourDates", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<TourDetailSchedule, QTourDetailSchedule> tourDetailScheduleList = this.<TourDetailSchedule, QTourDetailSchedule>createList("tourDetailScheduleList", TourDetailSchedule.class, QTourDetailSchedule.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTourSchedule(String variable) {
        this(TourSchedule.class, forVariable(variable), INITS);
    }

    public QTourSchedule(Path<? extends TourSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTourSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTourSchedule(PathMetadata metadata, PathInits inits) {
        this(TourSchedule.class, metadata, inits);
    }

    public QTourSchedule(Class<? extends TourSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tour = inits.isInitialized("tour") ? new QTour(forProperty("tour"), inits.get("tour")) : null;
    }

}


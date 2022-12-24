package com.koing.server.koing_server.domain.tour;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourCategory is a Querydsl query type for TourCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourCategory extends EntityPathBase<TourCategory> {

    private static final long serialVersionUID = 1565445086L;

    public static final QTourCategory tourCategory = new QTourCategory("tourCategory");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final SetPath<Tour, QTour> categorizedTours = this.<Tour, QTour>createSet("categorizedTours", Tour.class, QTour.class, PathInits.DIRECT2);

    public final StringPath categoryName = createString("categoryName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SetPath<String, StringPath> detailTypes = this.<String, StringPath>createSet("detailTypes", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTourCategory(String variable) {
        super(TourCategory.class, forVariable(variable));
    }

    public QTourCategory(Path<? extends TourCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourCategory(PathMetadata metadata) {
        super(TourCategory.class, metadata);
    }

}


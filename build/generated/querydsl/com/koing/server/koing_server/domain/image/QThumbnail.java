package com.koing.server.koing_server.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThumbnail is a Querydsl query type for Thumbnail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThumbnail extends EntityPathBase<Thumbnail> {

    private static final long serialVersionUID = -1875533589L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThumbnail thumbnail = new QThumbnail("thumbnail");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originName = createString("originName");

    public final com.koing.server.koing_server.domain.tour.QTour ownedTour;

    public final NumberPath<Integer> thumbnailOrder = createNumber("thumbnailOrder", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QThumbnail(String variable) {
        this(Thumbnail.class, forVariable(variable), INITS);
    }

    public QThumbnail(Path<? extends Thumbnail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThumbnail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThumbnail(PathMetadata metadata, PathInits inits) {
        this(Thumbnail.class, metadata, inits);
    }

    public QThumbnail(Class<? extends Thumbnail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ownedTour = inits.isInitialized("ownedTour") ? new com.koing.server.koing_server.domain.tour.QTour(forProperty("ownedTour"), inits.get("ownedTour")) : null;
    }

}


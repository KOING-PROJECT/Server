package com.koing.server.koing_server.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QThumbnail is a Querydsl query type for Thumbnail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThumbnail extends EntityPathBase<Thumbnail> {

    private static final long serialVersionUID = -1875533589L;

    public static final QThumbnail thumbnail = new QThumbnail("thumbnail");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final StringPath originName = createString("originName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QThumbnail(String variable) {
        super(Thumbnail.class, forVariable(variable));
    }

    public QThumbnail(Path<? extends Thumbnail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QThumbnail(PathMetadata metadata) {
        super(Thumbnail.class, metadata);
    }

}


package com.koing.server.koing_server.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostPhoto is a Querydsl query type for PostPhoto
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostPhoto extends EntityPathBase<PostPhoto> {

    private static final long serialVersionUID = -1217304207L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostPhoto postPhoto = new QPostPhoto("postPhoto");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originName = createString("originName");

    public final com.koing.server.koing_server.domain.post.QPost ownedPost;

    public final NumberPath<Integer> postPhotoOrder = createNumber("postPhotoOrder", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPostPhoto(String variable) {
        this(PostPhoto.class, forVariable(variable), INITS);
    }

    public QPostPhoto(Path<? extends PostPhoto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostPhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostPhoto(PathMetadata metadata, PathInits inits) {
        this(PostPhoto.class, metadata, inits);
    }

    public QPostPhoto(Class<? extends PostPhoto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ownedPost = inits.isInitialized("ownedPost") ? new com.koing.server.koing_server.domain.post.QPost(forProperty("ownedPost"), inits.get("ownedPost")) : null;
    }

}


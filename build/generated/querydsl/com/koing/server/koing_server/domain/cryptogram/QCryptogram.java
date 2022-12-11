package com.koing.server.koing_server.domain.cryptogram;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCryptogram is a Querydsl query type for Cryptogram
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCryptogram extends EntityPathBase<Cryptogram> {

    private static final long serialVersionUID = 1071830848L;

    public static final QCryptogram cryptogram1 = new QCryptogram("cryptogram1");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath cryptogram = createString("cryptogram");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath targetEmail = createString("targetEmail");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final BooleanPath verified = createBoolean("verified");

    public QCryptogram(String variable) {
        super(Cryptogram.class, forVariable(variable));
    }

    public QCryptogram(Path<? extends Cryptogram> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCryptogram(PathMetadata metadata) {
        super(Cryptogram.class, metadata);
    }

}


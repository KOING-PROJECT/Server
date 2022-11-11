package com.koing.server.koing_server.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserOptionalInfo is a Querydsl query type for UserOptionalInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOptionalInfo extends EntityPathBase<UserOptionalInfo> {

    private static final long serialVersionUID = -1910557970L;

    public static final QUserOptionalInfo userOptionalInfo = new QUserOptionalInfo("userOptionalInfo");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath job = createString("job");

    public final StringPath jobArea = createString("jobArea");

    public final StringPath universityAndMajor = createString("universityAndMajor");

    public QUserOptionalInfo(String variable) {
        super(UserOptionalInfo.class, forVariable(variable));
    }

    public QUserOptionalInfo(Path<? extends UserOptionalInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserOptionalInfo(PathMetadata metadata) {
        super(UserOptionalInfo.class, metadata);
    }

}


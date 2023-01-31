package com.koing.server.koing_server.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOptionalInfo is a Querydsl query type for UserOptionalInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOptionalInfo extends EntityPathBase<UserOptionalInfo> {

    private static final long serialVersionUID = -1910557970L;

    public static final QUserOptionalInfo userOptionalInfo = new QUserOptionalInfo("userOptionalInfo");

    public final SetPath<String, StringPath> areas = this.<String, StringPath>createSet("areas", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath company = createString("company");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final BooleanPath isCertified = createBoolean("isCertified");

    public final StringPath job = createString("job");

    public final SetPath<String, StringPath> languages = this.<String, StringPath>createSet("languages", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath universityEmail = createString("universityEmail");

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


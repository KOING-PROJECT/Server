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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOptionalInfo userOptionalInfo = new QUserOptionalInfo("userOptionalInfo");

    public final StringPath ageRange = createString("ageRange");

    public final SetPath<String, StringPath> areas = this.<String, StringPath>createSet("areas", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath company = createString("company");

    public final StringPath description = createString("description");

    public final EnumPath<GenderType> gender = createEnum("gender", GenderType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final BooleanPath isCertified = createBoolean("isCertified");

    public final StringPath job = createString("job");

    public final SetPath<String, StringPath> languages = this.<String, StringPath>createSet("languages", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath universityEmail = createString("universityEmail");

    public final QUser user;

    public QUserOptionalInfo(String variable) {
        this(UserOptionalInfo.class, forVariable(variable), INITS);
    }

    public QUserOptionalInfo(Path<? extends UserOptionalInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOptionalInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOptionalInfo(PathMetadata metadata, PathInits inits) {
        this(UserOptionalInfo.class, metadata, inits);
    }

    public QUserOptionalInfo(Class<? extends UserOptionalInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


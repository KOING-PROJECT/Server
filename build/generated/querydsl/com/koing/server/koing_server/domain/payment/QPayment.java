package com.koing.server.koing_server.domain.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 1386974960L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.koing.server.koing_server.domain.user.QUser guide;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imp_uid = createString("imp_uid");

    public final StringPath merchant_uid = createString("merchant_uid");

    public final NumberPath<Integer> paymentAmount = createNumber("paymentAmount", Integer.class);

    public final StringPath paymentGatewayName = createString("paymentGatewayName");

    public final StringPath paymentMethod = createString("paymentMethod");

    public final com.koing.server.koing_server.domain.tour.QTourApplication paymentProduct;

    public final StringPath paymentStatus = createString("paymentStatus");

    public final StringPath paymentTime = createString("paymentTime");

    public final StringPath paymentUnit = createString("paymentUnit");

    public final com.koing.server.koing_server.domain.user.QUser tourist;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.guide = inits.isInitialized("guide") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("guide"), inits.get("guide")) : null;
        this.paymentProduct = inits.isInitialized("paymentProduct") ? new com.koing.server.koing_server.domain.tour.QTourApplication(forProperty("paymentProduct"), inits.get("paymentProduct")) : null;
        this.tourist = inits.isInitialized("tourist") ? new com.koing.server.koing_server.domain.user.QUser(forProperty("tourist"), inits.get("tourist")) : null;
    }

}


package com.koing.server.koing_server.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1519278752L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.koing.server.koing_server.domain.common.QAuditingTimeEntity _super = new com.koing.server.koing_server.domain.common.QAuditingTimeEntity(this);

    public final com.koing.server.koing_server.domain.account.QAccount account;

    public final NumberPath<Integer> accumulatedApprovalTourCount = createNumber("accumulatedApprovalTourCount", Integer.class);

    public final NumberPath<Integer> accumulatedReportedCount = createNumber("accumulatedReportedCount", Integer.class);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Double> attachment = createNumber("attachment", Double.class);

    public final StringPath birthDate = createString("birthDate");

    public final SetPath<com.koing.server.koing_server.domain.payment.Payment, com.koing.server.koing_server.domain.payment.QPayment> buyPayments = this.<com.koing.server.koing_server.domain.payment.Payment, com.koing.server.koing_server.domain.payment.QPayment>createSet("buyPayments", com.koing.server.koing_server.domain.payment.Payment.class, com.koing.server.koing_server.domain.payment.QPayment.class, PathInits.DIRECT2);

    public final SetPath<Integer, NumberPath<Integer>> categoryIndexes = this.<Integer, NumberPath<Integer>>createSet("categoryIndexes", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final StringPath country = createString("country");

    public final SetPath<com.koing.server.koing_server.domain.post.Comment, com.koing.server.koing_server.domain.post.QComment> createComments = this.<com.koing.server.koing_server.domain.post.Comment, com.koing.server.koing_server.domain.post.QComment>createSet("createComments", com.koing.server.koing_server.domain.post.Comment.class, com.koing.server.koing_server.domain.post.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SetPath<com.koing.server.koing_server.domain.post.Post, com.koing.server.koing_server.domain.post.QPost> createPosts = this.<com.koing.server.koing_server.domain.post.Post, com.koing.server.koing_server.domain.post.QPost>createSet("createPosts", com.koing.server.koing_server.domain.post.Post.class, com.koing.server.koing_server.domain.post.QPost.class, PathInits.DIRECT2);

    public final SetPath<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour> createTours = this.<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour>createSet("createTours", com.koing.server.koing_server.domain.tour.Tour.class, com.koing.server.koing_server.domain.tour.QTour.class, PathInits.DIRECT2);

    public final NumberPath<Integer> currentRemainAmount = createNumber("currentRemainAmount", Integer.class);

    public final SetPath<com.koing.server.koing_server.domain.payment.Payment, com.koing.server.koing_server.domain.payment.QPayment> earnPayments = this.<com.koing.server.koing_server.domain.payment.Payment, com.koing.server.koing_server.domain.payment.QPayment>createSet("earnPayments", com.koing.server.koing_server.domain.payment.Payment.class, com.koing.server.koing_server.domain.payment.QPayment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final SetPath<User, QUser> follower = this.<User, QUser>createSet("follower", User.class, QUser.class, PathInits.DIRECT2);

    public final SetPath<User, QUser> following = this.<User, QUser>createSet("following", User.class, QUser.class, PathInits.DIRECT2);

    public final EnumPath<GenderType> gender = createEnum("gender", GenderType.class);

    public final EnumPath<com.koing.server.koing_server.common.enums.GuideGrade> guideGrade = createEnum("guideGrade", com.koing.server.koing_server.common.enums.GuideGrade.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<com.koing.server.koing_server.domain.post.Post, com.koing.server.koing_server.domain.post.QPost> likePosts = this.<com.koing.server.koing_server.domain.post.Post, com.koing.server.koing_server.domain.post.QPost>createSet("likePosts", com.koing.server.koing_server.domain.post.Post.class, com.koing.server.koing_server.domain.post.QPost.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final SetPath<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour> pressLikeTours = this.<com.koing.server.koing_server.domain.tour.Tour, com.koing.server.koing_server.domain.tour.QTour>createSet("pressLikeTours", com.koing.server.koing_server.domain.tour.Tour.class, com.koing.server.koing_server.domain.tour.QTour.class, PathInits.DIRECT2);

    public final SetPath<String, StringPath> roles = this.<String, StringPath>createSet("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalEarnAmount = createNumber("totalEarnAmount", Integer.class);

    public final NumberPath<Integer> totalTourists = createNumber("totalTourists", Integer.class);

    public final EnumPath<com.koing.server.koing_server.common.enums.TouristGrade> touristGrade = createEnum("touristGrade", com.koing.server.koing_server.common.enums.TouristGrade.class);

    public final SetPath<com.koing.server.koing_server.domain.tour.TourParticipant, com.koing.server.koing_server.domain.tour.QTourParticipant> tourParticipants = this.<com.koing.server.koing_server.domain.tour.TourParticipant, com.koing.server.koing_server.domain.tour.QTourParticipant>createSet("tourParticipants", com.koing.server.koing_server.domain.tour.TourParticipant.class, com.koing.server.koing_server.domain.tour.QTourParticipant.class, PathInits.DIRECT2);

    public final NumberPath<Integer> tourProgressCount = createNumber("tourProgressCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserOptionalInfo userOptionalInfo;

    public final EnumPath<com.koing.server.koing_server.common.enums.UserStatus> userStatus = createEnum("userStatus", com.koing.server.koing_server.common.enums.UserStatus.class);

    public final StringPath withdrawalAt = createString("withdrawalAt");

    public final StringPath withdrawalReason = createString("withdrawalReason");

    public final NumberPath<Integer> withdrawAmount = createNumber("withdrawAmount", Integer.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.koing.server.koing_server.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.userOptionalInfo = inits.isInitialized("userOptionalInfo") ? new QUserOptionalInfo(forProperty("userOptionalInfo"), inits.get("userOptionalInfo")) : null;
    }

}


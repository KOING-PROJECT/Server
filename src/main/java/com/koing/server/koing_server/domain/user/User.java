package com.koing.server.koing_server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User extends AuditingTimeEntity {

    @Builder
    public User(String email, String password
            , String phoneNumber, String name, String birthDate
            , String country, GenderType gender, int age,
                boolean enabled, Set<String> roles,
                UserOptionalInfo userOptionalInfo,
                Set<TourParticipant> tourParticipants,
                Set<Tour> createTours,
                Set<Tour> pressLikeTours,
                Set<User> following,
                Set<User> follower
                ) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
        this.roles = roles;
        this.gender = gender;
        this.age = age;
        this.enabled = enabled;
        this.userOptionalInfo = userOptionalInfo;
        this.tourParticipants = tourParticipants;
        this.createTours = createTours;
        this.pressLikeTours = pressLikeTours;
        this.following = following;
        this.follower = follower;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String birthDate;

    @Column(length = 50, nullable = false)
    private String country;

//    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(length = 20, nullable = false, name = "roles")
    private Set<String> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column
    private int age;

    @Column
    private boolean enabled;

    // orphanRemoval = true 이므로 부모 entity에서 자식 entity를 삭제하면 자식 entity가 삭제됨.
    @JoinColumn(name = "user_optional_info_id")
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private UserOptionalInfo userOptionalInfo;

    // 신청한 투어
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participant")
    private Set<TourParticipant> tourParticipants;

    // 생성한 투어
//    @JsonBackReference
    // jsonignore 하고 user가 만든 tour가 필요하면 dto로 전달해주자
    @JsonIgnore
    @OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
    private Set<Tour> createTours;

    // 좋아요 누른 투어
    @JsonIgnore
    @ManyToMany(mappedBy = "pressLikeUsers", fetch = FetchType.LAZY)
    private Set<Tour> pressLikeTours;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "like_guide_table",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "guide_id"))
    private Set<User> following;

    @JsonIgnore
    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private Set<User> follower;

//    소셜 로그인시 사용
//    private SocialInfo socialInfo;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public void setUserOptionalInfo(UserOptionalInfo userOptionalInfo) {
        this.userOptionalInfo = userOptionalInfo;
    }

    public void pressFollow(User targetUser) {
        if (this.following == null || !(this.following.contains(targetUser))) {
            addFollow(targetUser);
        }
        else {
            deleteFollow(targetUser);
        }
    }

    private void addFollow(User targetUser) {
        if (this.following == null) {
            this.following = new HashSet<>();
        }
        this.following.add(targetUser);

        if (targetUser.getFollower() == null) {
            Set<User> follower = new HashSet<>();
            targetUser.setFollower(follower);
        }
        targetUser.getFollower().add(this);
    }

    private void deleteFollow(User targetUser) {
        this.following.remove(targetUser);

        if (targetUser.getFollower() != null) {
            targetUser.getFollower().remove(this);
        }
    }
}

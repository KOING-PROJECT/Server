package com.koing.server.koing_server.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
                Set<TourApplication> tourApplication,
                Set<Tour> createTours
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
        this.tourApplication = tourApplication;
        this.createTours = createTours;
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

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserOptionalInfo userOptionalInfo;

    // 신청한 투어
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<TourApplication> tourApplication;

    // 생성한 투어
//    @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "createUser", fetch = FetchType.EAGER)
    private Set<Tour> createTours;

//    소셜 로그인시 사용
//    private SocialInfo socialInfo;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public static User newUser(String email, String password, String phoneNumber,
            String name, String birthDate, String country, GenderType gender, int age, Set<String> roles, boolean enabled) {
        return User.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .country(country)
                .roles(roles)
                .gender(gender)
                .age(age)
                .enabled(enabled)
                .userOptionalInfo(null)
                .build();
    }
}

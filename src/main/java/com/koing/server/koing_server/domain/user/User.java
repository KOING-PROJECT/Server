package com.koing.server.koing_server.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User extends AuditingTimeEntity {

    @Builder
    public User(String email, String password
            , String phoneNumber, String name, String birthDate
            , String country, GenderType gender, int age, boolean enabled, UserOptionalInfo userOptionalInfo) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
        this.gender = gender;
        this.age = age;
        this.enabled = enabled;
        this.userOptionalInfo = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 16, nullable = false)
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

//    소셜 로그인시 사용
//    private SocialInfo socialInfo;


    public static User newInstance(String email, String password, String phoneNumber,
            String name, String birthDate, String country, GenderType gender, int age, boolean enabled) {
        return User.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .country(country)
                .gender(gender)
                .age(age)
                .enabled(enabled)
                .userOptionalInfo(null)
                .build();
    }
}

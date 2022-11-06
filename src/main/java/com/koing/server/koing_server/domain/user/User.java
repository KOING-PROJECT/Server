package com.koing.server.koing_server.domain.user;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {

    @Builder
    public User(String email, String password
            , String phoneNumber, String name, String birthDate
            , String country, GenderType gender, int age, UserOptionalInfo userOptionalInfo) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
        this.gender = gender;
        this.age = age;
        this.userOptionalInfo = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 16, nullable = false)
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
    private GenderType gender;

    @Column
    private int age;

    // orphanRemoval = true 이므로 부모 entity에서 자식 entity를 삭제하면 자식 entity가 삭제됨.
    @JoinColumn(name = "userOptionalInfoId")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserOptionalInfo userOptionalInfo;

//    소셜 로그인시 사용
//    private SocialInfo socialInfo;


    public static User newInstance(String email, String password, String phoneNumber,
            String name, String birthDate, String country, GenderType gender, int age) {
        return User.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .country(country)
                .gender(gender)
                .age(age)
                .userOptionalInfo(null)
                .build();
    }
}

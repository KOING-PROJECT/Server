package com.koing.server.koing_server.domain.user;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {

    @Builder
    public User(String name, GenderType gender, int age, String phoneNumber, String address, String imageUrl) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column
    private int age;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 100)
    private String address;

    @Column(length = 300)
    private String imageUrl;


    public static User newInstance(String name, GenderType gender, int age, String phoneNumber, String address, String imageUrl) {
        return User.builder()
                .name(name)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .address(address)
                .imageUrl(imageUrl)
                .build();
    }


//    소셜 로그인시 사용
//    private SocialInfo socialInfo;




}

package com.koing.server.koing_server.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOptionalInfo {

    @Id
    @Column(name = "userOptionalInfoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String imageUrl;

    @Column(length = 20)
    private String job;

    @Column(length = 50)
    private String jobArea;

    @Column(length = 50)
    private String universityAndMajor;

    @Column(length = 100)
    private String address;

}

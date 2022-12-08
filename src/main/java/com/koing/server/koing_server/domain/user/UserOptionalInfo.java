package com.koing.server.koing_server.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOptionalInfo {

    @Id
    @Column(name = "user_optional_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String imageUrl;

    @Column(length = 300)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 20, nullable = false, name = "languages")
    private List<String> languages;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 20, name = "areas")
    private List<String> areas;

    @Column(length = 20)
    private String job;

    @Column(length = 50)
    private String universityEmail;

    @Column(length = 50)
    private String company;

}

package com.koing.server.koing_server.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    private Set<String> languages;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 20, name = "areas")
    private Set<String> areas;

    @Column(length = 20)
    private String job;

    @Column(length = 50)
    private String universityEmail;

    @Column(length = 50)
    private String company;

}

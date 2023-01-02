package com.koing.server.koing_server.domain.user;

import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
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

    public UserOptionalInfo(UserOptionalInfoCreateDto userOptionalInfoCreateDto) {
        this.imageUrl = userOptionalInfoCreateDto.getImageUrl();
        this.description = userOptionalInfoCreateDto.getDescription();
        this.languages = userOptionalInfoCreateDto.getLanguages();
        this.areas = userOptionalInfoCreateDto.getAreas();
        this.job = userOptionalInfoCreateDto.getJob();
        this.universityEmail = userOptionalInfoCreateDto.getUniversityEmail();
        this.company = userOptionalInfoCreateDto.getCompany();
    }

    @Id
    @Column(name = "user_optional_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String imageUrl;

    @Column(length = 300)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 20, name = "languages")
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

    private boolean isCertified;

}

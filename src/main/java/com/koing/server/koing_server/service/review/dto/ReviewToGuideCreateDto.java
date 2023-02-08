package com.koing.server.koing_server.service.review.dto;

import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewToGuideCreateDto {

    private Long writeTouristId;
    private Long tourId;
    private String tourDate;
    private Set<String> tourReviews;
    private Set<String> guideReviews;
    private int attachment;
    private String totalReview;
//    private List<MultipartFile> reviewPhotos;

}

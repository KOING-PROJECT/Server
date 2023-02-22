package com.koing.server.koing_server.service.review.dto;

import com.koing.server.koing_server.domain.tour.TourParticipant;
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
public class ReviewToTouristCreateDto {

    private Long writeGuideId;
    private Long tourId;
    private String tourDate;
    private Long tourParticipantId;
    private Set<String> progressReviews;
    private Set<String> touristReviews;
    private int attachment;

}

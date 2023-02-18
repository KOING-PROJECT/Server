package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.tour.TourParticipant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTourParticipantDto {

    public UserTourParticipantDto(TourParticipant tourParticipant) {
        if (tourParticipant.getParticipant() != null) {
            this.touristId = tourParticipant.getParticipant().getId();
            this.touristName = tourParticipant.getParticipant().getName();

            if (tourParticipant.getParticipant().getUserOptionalInfo() != null) {
                this.touristProfileImage = tourParticipant.getParticipant().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
    }

    private Long touristId;
    private String touristName;
    private String touristProfileImage;

}

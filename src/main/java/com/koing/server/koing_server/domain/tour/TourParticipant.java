package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
@Getter
@Setter
@Table(name = "TOUR_PARTICIPANT_TABLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourParticipant {

    public TourParticipant(TourApplication tourApplication, User participant, int numberOfParticipants) {
        setTourApplication(tourApplication);
        setParticipant(participant);
        this.numberOfParticipants = numberOfParticipants;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TourApplication tourApplication;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User participant;

    private int numberOfParticipants;

    private void setTourApplication(TourApplication tourApplication) {
        this.tourApplication = tourApplication;

        if (tourApplication.getTourParticipants() == null) {
            tourApplication.setTourParticipants(new ArrayList<>());
        }

        tourApplication.getTourParticipants().add(this);
    }

    private void setParticipant(User user) {
        this.participant = user;

        if (user.getTourParticipants() == null) {
            user.setTourParticipants(new HashSet<>());
        }

        user.getTourParticipants().add(this);
    }

}

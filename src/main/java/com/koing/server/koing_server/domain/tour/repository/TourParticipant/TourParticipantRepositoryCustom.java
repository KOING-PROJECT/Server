package com.koing.server.koing_server.domain.tour.repository.TourParticipant;

import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;

import java.util.List;

public interface TourParticipantRepositoryCustom {

    List<TourParticipant> findTourParticipantByUser(User participant);

    TourParticipant findTourParticipantByTourApplicationId(Long tourApplicationId);

    TourParticipant findTourParticipantByTourParticipantId(Long tourParticipantId);

}

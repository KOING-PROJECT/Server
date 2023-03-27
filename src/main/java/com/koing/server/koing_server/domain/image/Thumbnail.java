package com.koing.server.koing_server.domain.image;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.tour.Tour;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TOUR_THUMBNAIL_TABLE")
public class Thumbnail extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int thumbnailOrder;

    private String originName;

    @Column(unique = true)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour ownedTour;

    public void setTour(Tour tour) {
        this.ownedTour = tour;

        if (tour.getThumbnails() == null) {
            tour.setThumbnails(new ArrayList<>());
        }

        tour.getThumbnails().add(this);
    }

    public void deleteTour(Tour tour) {
        this.ownedTour = null;

        tour.getThumbnails().remove(this);
    }

}

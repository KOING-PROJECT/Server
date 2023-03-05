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
@Table(name = "TOUR_IMAGE_TABLE")
public class Thumbnail extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int order;

    private String originName;

    @Column(unique = true)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour tour;

    public void setTour(Tour tour) {
        this.tour = tour;

        if (tour.getThumbnails() == null) {
            tour.setThumbnails(new ArrayList<>());
        }

        tour.getThumbnails().add(this);
    }

}

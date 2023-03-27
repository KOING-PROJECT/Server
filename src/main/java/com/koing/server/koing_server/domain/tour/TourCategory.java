package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Sets;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.service.tour.dto.TourCategoryCreateDto;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_CATEGORY_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourCategory extends AuditingTimeEntity {

    public TourCategory(TourCategoryCreateDto tourCategoryCreateDto) {
        this.categoryName = tourCategoryCreateDto.getCategoryName();
        this.detailTypes = Sets.newHashSet(tourCategoryCreateDto.getDetailTypes());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @ManyToMany(mappedBy = "tourCategories", fetch = FetchType.LAZY)
    private Set<Tour> categorizedTours;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(length = 20, nullable = false, name = "detail_type")
    private Set<String> detailTypes;

    public void setTour(Tour tour) {
        if (!this.categorizedTours.contains(tour)) {
            this.getCategorizedTours().add(tour);
        }
    }

    public void deleteTour(Tour tour) {
        if (this.categorizedTours.contains(tour)) {
            this.getCategorizedTours().remove(tour);
        }
    }

}

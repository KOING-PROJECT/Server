package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Sets;
import com.koing.server.koing_server.service.tour.dto.TourCategoryDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_CATEGORY_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @ManyToMany(mappedBy = "tourCategories", fetch = FetchType.LAZY)
    private Set<Tour> categorizedTours;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(length = 20, nullable = false, name = "detail_type")
    private Set<String> detailTypes;

    public TourCategory(TourCategoryDto tourCategoryDto) {
        this.categoryName = tourCategoryDto.getCategoryName();
        this.detailTypes = Sets.newHashSet(tourCategoryDto.getDetailTypes());
    }
}

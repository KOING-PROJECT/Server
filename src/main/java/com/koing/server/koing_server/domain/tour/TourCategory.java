package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 20, nullable = false, name = "detail_type")
    private Set<String> detailType;

}

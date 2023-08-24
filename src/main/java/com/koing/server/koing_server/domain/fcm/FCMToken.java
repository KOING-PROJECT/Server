package com.koing.server.koing_server.domain.fcm;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FCM_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FCMToken extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String fcmToken;

}

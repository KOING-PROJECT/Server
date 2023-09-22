package com.koing.server.koing_server.domain.jwt;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "JWT_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JwtToken extends AbstractRootEntity {

    @Builder
    public JwtToken(String userEmail, String accessToken) {
        this.userEmail = userEmail;
        this.accessToken = accessToken;
    }

    @Builder(builderMethodName = "InitBuilder")
    public JwtToken(String userEmail) {
        this.userEmail = userEmail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userEmail;

    private String accessToken;
}

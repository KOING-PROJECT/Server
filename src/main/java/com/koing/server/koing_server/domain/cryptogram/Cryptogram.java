package com.koing.server.koing_server.domain.cryptogram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cryptogram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cryptogram;

    private String targetEmail;

    private LocalDateTime createdAt;

    private boolean verified;

    public CryptogramResponse toCryptogramResponse() {
        return CryptogramResponse.builder()
                .verified(isVerified())
                .build();
    }

}

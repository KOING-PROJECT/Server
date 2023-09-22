package com.koing.server.koing_server.domain.cryptogram;

import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cryptogram extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cryptogram;

    private String targetEmail;

    private boolean verified;

}

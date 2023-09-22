package com.koing.server.koing_server.domain.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ACCOUNT_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Account extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;

    private String accountNumber;

    private String birthDate;

    private String registrationNumber;

    @OneToOne(fetch = FetchType.LAZY)
    private User accountOwner;

    public void setOwner(User user) {
        this.accountOwner = user;
        user.setAccount(this);
    }

}

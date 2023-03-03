package com.koing.server.koing_server.domain.sms;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SMS_TABLE")
public class SMS extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificationNumber;

    private String targetPhoneNumber;

    private boolean verified;

}

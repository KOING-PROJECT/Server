package com.koing.server.koing_server.domain.sms.repository;

import com.koing.server.koing_server.domain.sms.SMS;

public interface SMSRepositoryCustom {

    boolean hasSMSByTargetPhoneNumber(String targetPhoneNumber);
    SMS findSMSByTargetPhoneNumber(String targetPhoneNumber);

}

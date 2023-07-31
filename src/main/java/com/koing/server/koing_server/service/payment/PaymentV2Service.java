package com.koing.server.koing_server.service.payment;


import com.koing.server.koing_server.common.dto.SuperResponse;
import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentV2Service {

    @Value("${iamport.imp.secret}")
    private String impSecret;
    @Value("${iamport.imp.key}")
    private String impKey;

    private final IamportClient iamportClient;
    private final Logger LOGGER = LoggerFactory.getLogger(PaymentV2Service.class);

    public PaymentV2Service() {
        this.iamportClient = new IamportClient(impSecret, impKey);
    }


}

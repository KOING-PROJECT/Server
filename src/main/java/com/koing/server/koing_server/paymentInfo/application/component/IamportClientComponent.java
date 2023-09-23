package com.koing.server.koing_server.paymentInfo.application.component;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IamportClientComponent {

    @Value("${iamport.imp.secret}")
    private String impSecret;

    @Value("${iamport.imp.key}")
    private String impKey;

    private final IamportClient iamportClient;

    public IamportClientComponent() {
        this.iamportClient = new IamportClient(impSecret, impKey);
    }

    public IamportResponse<AccessToken> getAuth() throws IamportResponseException, IOException {
        return iamportClient.getAuth();
    }
}

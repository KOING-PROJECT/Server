package com.koing.server.koing_server.service.payment.fegin;


import com.koing.server.koing_server.common.dto.PortOneResponse;
import com.koing.server.koing_server.paymentInfo.application.dto.PortOneTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PortOneApiClient", url = "${iamport.server.host}", primary = false)
public interface PortOneTokenApiFeign {

    @PostMapping()
    PortOneResponse<PortOneTokenResponse> getPortOneAccessToken(
            @RequestParam("iam_key") String iamKey,
            @RequestParam("iam_secret") String iamSecret
    );

}

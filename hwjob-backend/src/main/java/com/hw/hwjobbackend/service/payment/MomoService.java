package com.hw.hwjobbackend.service.payment;

import com.hw.hwjobbackend.configuration.payment.MomoConfig;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointPaymentGatewayRequest;
import com.hw.hwjobbackend.model.dto.request.momo.MomoCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class MomoService  implements PaymentGatewayService{
    MomoConfig momoConfig;
    RestTemplate restTemplate = new RestTemplate();


    @Override
    public String createPaymentUrl(
            LoyaltyPointPaymentGatewayRequest paymentReq, HttpServletRequest request
    ) {

        String requestId = momoConfig.getPartnerCode() + System.currentTimeMillis();
//        String orderId = paymentReq.getPaymentId().replace("-", "");
        String orderId = paymentReq.getPaymentId();
        String extraData = Base64.getEncoder()
                .encodeToString(
                        ("userId=" + paymentReq.getUserId())
                                .getBytes(StandardCharsets.UTF_8)
                );

        String rawSignature =
                "accessKey=" + momoConfig.getAccessKey() +
                        "&amount=" + paymentReq.getAmount() +
                        "&extraData=" + extraData +
                        "&ipnUrl=" + momoConfig.getIpnUrl() +
                        "&orderId=" + orderId +
                        "&orderInfo=Top up reward point" +
                        "&partnerCode=" + momoConfig.getPartnerCode() +
                        "&redirectUrl=" + momoConfig.getRedirectUrl() +
                        "&requestId=" + requestId +
                        "&requestType=captureWallet";

        String signature = hmacSHA256(rawSignature, momoConfig.getSecretKey());

        MomoCreateRequest createRequest = MomoCreateRequest.builder()
                .partnerCode(momoConfig.getPartnerCode())
                .accessKey(momoConfig.getAccessKey())
                .requestId(requestId)
                .orderId(orderId)
                .amount(paymentReq.getAmount())
                .orderInfo("Top up reward point")
                .redirectUrl(momoConfig.getRedirectUrl())
                .ipnUrl(momoConfig.getIpnUrl())
                .extraData(extraData)
                .requestType("captureWallet")
                .signature(signature)
                .lang("en")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MomoCreateRequest> entity =
                new HttpEntity<>(createRequest, headers);

        Map<String, Object> resp =
                restTemplate.postForObject(momoConfig.getEndpoint(), entity, Map.class);

        return (String) resp.get("payUrl");
    }

    @Override
    public void verifySignature(Map<String, String> payload) {

        String receivedSignature = (String) payload.get("signature");
        if (receivedSignature == null) {
            throw new RuntimeException("Missing MoMo signature");
        }

        String rawSignature =
                "accessKey=" + momoConfig.getAccessKey() +
                        "&amount=" + payload.get("amount") +
                        "&extraData=" + payload.get("extraData") +
                        "&message=" + payload.get("message") +
                        "&orderId=" + payload.get("orderId") +
                        "&orderInfo=" + payload.get("orderInfo") +
                        "&orderType=" + payload.get("orderType") +
                        "&partnerCode=" + payload.get("partnerCode") +
                        "&payType=" + payload.get("payType") +
                        "&requestId=" + payload.get("requestId") +
                        "&responseTime=" + payload.get("responseTime") +
                        "&resultCode=" + payload.get("resultCode") +
                        "&transId=" + payload.get("transId");

        String expectedSignature = hmacSHA256(rawSignature, momoConfig.getSecretKey());

        if (!expectedSignature.equals(receivedSignature)) {
            throw new RuntimeException("Invalid MoMo IPN signature");
        }
    }

    public static String hmacSHA256(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error signing MoMo request", e);
        }
    }

}

package com.hw.hwjobbackend.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.hwjobbackend.configuration.payment.VnPayConfig;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointPaymentGatewayRequest;
import com.hw.hwjobbackend.model.entity.loyalty_point.LoyaltyPointPayment;
import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;
import com.hw.hwjobbackend.repository.loyalty_point.LoyaltyPointPaymentRepository;
import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class VnPayService implements PaymentGatewayService {

    VnPayConfig config;

    LoyaltyPointPaymentRepository loyaltyPointPaymentRepository;
    BlockchainService blockchainService;

    @Override
    public String createPaymentUrl(LoyaltyPointPaymentGatewayRequest req,
                                   HttpServletRequest request) {
//        String txnRef = req.getPaymentId().replace("-", "");
        String txnRef = req.getPaymentId();
        long amount = req.getAmount() * 100; // VNPay dùng VND * 100

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", config.getTmnCode());
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", "Top up reward point");
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", config.getReturnUrl());
        params.put("vnp_IpAddr", request.getRemoteAddr());
        params.put("vnp_CreateDate",
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        String query = params.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String hash = hmacSHA512(query, config.getHashSecret());

        return config.getPayUrl() + "?" + query + "&vnp_SecureHash=" + hash;
    }
    public static String hmacSHA512(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec =
                    new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error signing VNPay request", e);
        }
    }
    @Override
    public void verifySignature(Map<String, String> params) {
        String secureHash = params.get("vnp_SecureHash");

        // 1. Sắp xếp tham số bằng TreeMap
        Map<String, String> hashableParams = new TreeMap<>(params);
        hashableParams.remove("vnp_SecureHash");
        hashableParams.remove("vnp_SecureHashType");

        // 2. Nối chuỗi TRỰC TIẾP, không Encode lại giá trị
        // Vì Spring đã decode dấu '+' thành ' ' hoặc giữ nguyên tùy môi trường
        // Chúng ta cần đảm bảo OrderInfo dùng dấu '+' nếu đó là thứ VNPay mong muốn
        StringBuilder data = new StringBuilder();
        for (Map.Entry<String, String> entry : hashableParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                data.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        // Xóa dấu & cuối cùng
        if (data.length() > 0) {
            data.setLength(data.length() - 1);
        }

        String query = data.toString();

        // 3. Nếu vẫn sai, hãy thử replace khoảng trắng thành dấu '+' (Vì VNPay Sandbox hay dùng chuẩn này)
        // String queryFinal = query.replace(" ", "+");

        String calculated = hmacSHA512(query, config.getHashSecret().trim());

        System.out.println("--- DEBUG FINAL ---");
        System.out.println("SignData: " + query);
        System.out.println("Calculated: " + calculated);
        System.out.println("Expected: " + secureHash);

        if (!calculated.equalsIgnoreCase(secureHash)) {
            // Thử phương án dự phòng với dấu cộng
            String queryBackup = query.replace(" ", "+");
            String calculatedBackup = hmacSHA512(queryBackup, config.getHashSecret().trim());
            if (calculatedBackup.equalsIgnoreCase(secureHash)) {
                return; // Thành công với phương án dự phòng
            }
            throw new RuntimeException("VNPay signature invalid");
        }
    }
}

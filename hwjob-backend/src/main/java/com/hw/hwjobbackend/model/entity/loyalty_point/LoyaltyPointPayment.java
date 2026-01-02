package com.hw.hwjobbackend.model.entity.loyalty_point;

import com.hw.hwjobbackend.model.enums.PaymentMethodEnum;
import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;
import com.hw.hwjobbackend.model.enums.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_point_payment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class LoyaltyPointPayment {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    Long grossAmount; // số tiền user trả

    Long feeAmount;   // phí cổng
    Long netAmount;   // tiền thực nhận

    @Column(nullable = false)
    BigInteger points;

    @Enumerated(EnumType.STRING)
    PaymentStatusEnum status;

    @Enumerated(EnumType.STRING)
    PaymentMethodEnum paymentMethod; // MOMO | VNPAY

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentTypeEnum paymentType;

    // Gateway common
    String gatewayOrderId;
    String gatewayRequestId;
    String gatewayTransactionId;

//     Withdraw MoMo
    String momoPhone;
    String momoName;

    // Chống IPN retry
    @Column(unique = true)
    String idempotentKey;

    // Debug / audit
    @Column(columnDefinition = "TEXT")
    String rawIpnPayload;

    // Blockchain
    String blockchainTxHash;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

}

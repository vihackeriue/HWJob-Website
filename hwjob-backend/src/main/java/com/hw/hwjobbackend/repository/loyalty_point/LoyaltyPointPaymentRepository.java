package com.hw.hwjobbackend.repository.loyalty_point;

import com.hw.hwjobbackend.model.entity.loyalty_point.LoyaltyPointPayment;
import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoyaltyPointPaymentRepository extends JpaRepository<LoyaltyPointPayment, String> {

    Page<LoyaltyPointPayment> findByUserIdAndStatusOrderByCreatedAtDesc(
            String userId,
            PaymentStatusEnum status,
            Pageable pageable
    );

    List<LoyaltyPointPayment> findByUserIdAndStatusOrderByCreatedAtDesc(
            String userId,
            PaymentStatusEnum status
    );
}

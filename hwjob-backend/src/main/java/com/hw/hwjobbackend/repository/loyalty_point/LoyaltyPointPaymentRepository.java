package com.hw.hwjobbackend.repository.loyalty_point;

import com.hw.hwjobbackend.model.entity.loyalty_point.LoyaltyPointPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyPointPaymentRepository extends JpaRepository<LoyaltyPointPayment, String> {
}

package org.happinessmeta.last.payment.repository;

import org.happinessmeta.last.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
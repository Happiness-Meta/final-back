package org.happinessmeta.last.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.payment.domain.type.PaymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

//    결제 비용
    private int paymentAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

//    결제 고유 번호
    private String paymentUid;

    private String cardName;

    private String payMethod;

    private String cardNumber;

    private String buyerEmail;

    private String buyerTel;

    // 통화
    private String currency;

    private String paidAt;

    private String payApiStatus;

    public Payment createPayment(int paymentAmount) {
        Payment payment = new Payment();
        payment.paymentAmount = paymentAmount;
        payment.status = PaymentStatus.READY;

        return payment;
    }

    public void changePaymentBySuccess(String payApiStatus, String paymentUid, String payMethod, String cardName, String cardNumber, String currency) {
        this.status = PaymentStatus.OK;
        this.paymentUid = paymentUid;
        this.payApiStatus = payApiStatus;
        this.payMethod = payMethod;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.currency = currency;
    }

    @Column(name = "exp_time", nullable = false, updatable = false)
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime expTime;
}

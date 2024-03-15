package org.happinessmeta.last.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //TODO: 결제 방법, 카드 번호, 카드 종류 등 추가

    public Payment createPayment(int paymentAmount) {
        Payment payment = new Payment();
        payment.paymentAmount = paymentAmount;
        payment.status = PaymentStatus.READY;

        return payment;
    }

    public void changePaymentBySuccess(PaymentStatus status, String paymentUid) {
        this.status = status;
        this.paymentUid = paymentUid;
    }
}

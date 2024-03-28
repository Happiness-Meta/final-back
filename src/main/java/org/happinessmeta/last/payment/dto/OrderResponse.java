package org.happinessmeta.last.payment.dto;

import lombok.Builder;
import org.happinessmeta.last.payment.domain.type.PaymentStatus;

public record OrderResponse(String itemName, String orderUid, PaymentOrderResponse payment) {
    public record PaymentOrderResponse(int paymentAmount, PaymentStatus status, String paymentUid, String paidAt) {
    }

    @Builder
    public OrderResponse(String itemName, String orderUid, PaymentOrderResponse payment) {
        PaymentOrderResponse paymentDto = new PaymentOrderResponse(
                payment.paymentAmount,
                payment.status,
                payment.paymentUid,
                payment.paidAt);

        this.itemName = itemName;
        this.orderUid = orderUid;
        this.payment = paymentDto;
    }
}

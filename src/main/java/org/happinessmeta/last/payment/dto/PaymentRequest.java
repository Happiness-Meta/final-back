package org.happinessmeta.last.payment.dto;

import lombok.Builder;
import lombok.Data;

public record PaymentRequest(
        String orderUid,
        String itemName,
        String buyerName,
        int paymentAmount,
        String buyerEmail
//      TODO: address 추가
//          String buyerAddress
) {
    @Builder
    public PaymentRequest (String orderUid, String itemName, String buyerName, int paymentAmount, String buyerEmail) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentAmount = paymentAmount;
        this.buyerEmail = buyerEmail;
//        this.buyerAddress = buyerAddress;
    }
}

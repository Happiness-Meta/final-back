package org.happinessmeta.last.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PaymentRequest {
    private String orderUid;
    private String itemName;
    private String buyerName;
    private int paymentPrice;
    private String buyerEmail;
    private String buyerAddress;

    @Builder
    public PaymentRequest(String orderUid, String itemName, String buyerName, int paymentPrice, String buyerEmail, String buyerAddress) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerEmail = buyerEmail;
        this.buyerAddress = buyerAddress;
    }
}

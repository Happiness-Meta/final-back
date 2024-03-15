package org.happinessmeta.last.payment.dto;

import lombok.Data;

@Data
public class PaymentCallbackRequest {
    private String paymentUid;
    private String orderUid;
}

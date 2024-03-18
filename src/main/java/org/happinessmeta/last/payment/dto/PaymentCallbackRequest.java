package org.happinessmeta.last.payment.dto;

import jakarta.validation.constraints.NotBlank;

public record PaymentCallbackRequest(
        @NotBlank(message = "결제 번호는 필수 값입니다.")
        String paymentUid,
        @NotBlank(message = "주문 번호는 필수 값입니다.")
        String orderUid) {
}

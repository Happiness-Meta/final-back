package org.happinessmeta.last.payment.dto;

import jakarta.validation.constraints.NotBlank;

public record PaymentCallbackRequest(
        @NotBlank(message = "결제 번호는 필수 값입니다.")
        String paymentUid,
        @NotBlank(message = "주문 번호는 필수 값입니다.")
        String orderUid,
        @NotBlank(message = "카드 이름은 필수 값입니다.")
        String cardName,
        @NotBlank(message = "카드 cvc값은 필수 값입니다.")
        String cardCode,
        @NotBlank(message = "카드 번호는 필수 값입니다.")
        String cardNumber,
        @NotBlank(message = "결제 방식은 필수 값입니다.")
        String payMethod
        ) {
}

package org.happinessmeta.last.payment.dto;

import jakarta.validation.constraints.NotBlank;

public record PaymentCallbackRequest(
        @NotBlank(message = "결제 번호는 필수 값입니다.")
        String impUid,
        @NotBlank(message = "주문 번호는 필수 값입니다.")
        String orderUid,
        @NotBlank(message = "카드 이름은 필수 값입니다.")
        String cardName,
        @NotBlank(message = "카드 번호는 필수 값입니다.")
        String cardNumber,
        @NotBlank(message = "결제 방식은 필수 값입니다.")
        String payMethod,
        @NotBlank(message = "결제 금액은 필수 값입니다.")
        String amount,
//        @NotBlank(message = "applyNum은 필수 값입니다.")
//        String applyNum,
//        @NotBlank(message = "구매자 주소 값은 필수 값입니다.")
//        String buyerAddr,
        @NotBlank(message = "구매자 이메일 값은 필수 값입니다.")
        String buyerEmail,
//        @NotBlank(message = "구매자 우편 번호 값은 필수 값입니다.")
//        String buyerPostcode,
        @NotBlank(message = "구매자 전화 번호 값은 필수 값입니다.")
        String buyerTel,
        @NotBlank(message = "통화 값은 필수 값입니다.")
        String currency,
        @NotBlank(message = "paidAt은 필수 값입니다.")
        String paidAt,
        @NotBlank(message = "결제 상태 값은 필수 값입니다.")
        String status
        ) {
}

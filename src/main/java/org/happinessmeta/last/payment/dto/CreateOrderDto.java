package org.happinessmeta.last.payment.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderDto(
        @NotBlank(message = "상품 가격은 필수 값입니다.")
        int itemPrice,
        @NotBlank(message = "상품명은 필수 값입니다.")
        String itemName
) {
        //TODO: of, from
}

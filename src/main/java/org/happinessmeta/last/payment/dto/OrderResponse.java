package org.happinessmeta.last.payment.dto;

import lombok.Builder;

public record OrderResponse(
        String itemName,
        int amount,
        String orderUid
) {
    @Builder
    public OrderResponse (String itemName, int amount, String orderUid) {
        this.itemName = itemName;
        this.amount = amount;
        this.orderUid = orderUid;
    }
}

package org.happinessmeta.last.payment.dto;

import lombok.Builder;
import org.happinessmeta.last.user.domain.User;

public record OrderPaidResponse(
        String itemName,
        int amount,
        String orderUid,
        User user
) {
    @Builder
    public OrderPaidResponse(String itemName, int amount, String orderUid, User user) {
        this.itemName = itemName;
        this.amount = amount;
        this.orderUid = orderUid;
        this.user = user;
    }
}

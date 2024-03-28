package org.happinessmeta.last.payment.dto;

import lombok.Builder;
import org.happinessmeta.last.payment.domain.type.PaymentStatus;
import org.happinessmeta.last.user.dto.UserResponse;

public record OrderPaidResponse(
    String itemName,
    int amount,
    String orderUid,
    UserResponse user,
    PaymentStatus status
) {
    @Builder
    public OrderPaidResponse(String itemName, int amount, String orderUid, UserResponse user, PaymentStatus status) {
            this.itemName = itemName;
            this.amount = amount;
            this.orderUid = orderUid;
            this.user = user;
            this.status = status;
        }
}

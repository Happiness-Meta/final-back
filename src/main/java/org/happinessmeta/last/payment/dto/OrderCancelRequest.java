package org.happinessmeta.last.payment.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.happinessmeta.last.payment.domain.Order}
 */
public record OrderCancelRequest(
        String orderUid,
        int cancelRequestAmount,
        String reason,
        String impUid
)   {

}
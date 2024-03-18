package org.happinessmeta.last.payment.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link org.happinessmeta.last.payment.domain.Order}
 */
public record DeleteOrderDto(int price, @NotNull String itemName, @NotNull String orderUid) implements Serializable {
}
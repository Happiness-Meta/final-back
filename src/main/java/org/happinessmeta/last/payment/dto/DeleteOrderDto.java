package org.happinessmeta.last.payment.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record DeleteOrderDto(int price, @NotNull String itemName, @NotNull String orderUid) implements Serializable {
}
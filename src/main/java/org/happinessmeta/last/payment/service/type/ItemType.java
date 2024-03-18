package org.happinessmeta.last.payment.service.type;

import lombok.Getter;

@Getter
public enum ItemType {
    BASIC_TICKET("00", 5900);

    private final String itemNumber;
    private final int itemPrice;

    ItemType(String itemNumber, int itemPrice){
        this.itemNumber = itemNumber;
        this.itemPrice = itemPrice;
    }
}

package org.happinessmeta.last.common.exception;

public class OrderPriceNotEqualsException extends RuntimeException {
    public OrderPriceNotEqualsException() {
        super();
    }

    public OrderPriceNotEqualsException(String message) {
        super(message);
    }

    public OrderPriceNotEqualsException(String message, Throwable cause) {
        super(message, cause);
    }
}

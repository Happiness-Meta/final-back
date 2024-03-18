package org.happinessmeta.last.common.exception;

public class OrderDetailNotFoundException extends RuntimeException{
    public OrderDetailNotFoundException() {
        super();
    }

    public OrderDetailNotFoundException(String message) {
        super(message);
    }

    public OrderDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

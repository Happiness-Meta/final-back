package org.happinessmeta.last.common.exception;

public class PaymentNotCompletedException extends RuntimeException {
    public PaymentNotCompletedException() {
        super();
    }

    public PaymentNotCompletedException(String message) {
        super(message);
    }

    public PaymentNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}

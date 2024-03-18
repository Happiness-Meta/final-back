package org.happinessmeta.last.common.exception;

public class PaymentProcessingException extends RuntimeException{
    public PaymentProcessingException() {
    }

    public PaymentProcessingException(String message) {
        super(message);
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

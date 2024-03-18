package org.happinessmeta.last.common.exception;

public class PaymentAmountIllegalException extends RuntimeException{
    public PaymentAmountIllegalException() {
        super();
    }

    public PaymentAmountIllegalException(String message) {
        super(message);
    }

    public PaymentAmountIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}

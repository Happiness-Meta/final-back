package org.happinessmeta.last.common.exception;

public class ExistUserException extends RuntimeException {
    public ExistUserException() {
    }

    public ExistUserException(String message) {
        super(message);
    }

    public ExistUserException(String message, Throwable cause) {
        super(message, cause);
    }
}

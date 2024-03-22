package org.happinessmeta.last.common.exception;

public class EmailPatternException extends RuntimeException {
    public EmailPatternException() {
    }

    public EmailPatternException(String message) {
        super(message);
    }

    public EmailPatternException(String message, Throwable cause) {
        super(message, cause);
    }
}

package org.happinessmeta.last.common.exception;

public class PasswordPatternException extends RuntimeException {
    public PasswordPatternException() {
    }

    public PasswordPatternException(String message) {
        super(message);
    }

    public PasswordPatternException(String message, Throwable cause) {
        super(message, cause);
    }
}

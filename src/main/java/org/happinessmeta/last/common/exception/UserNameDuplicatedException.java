package org.happinessmeta.last.common.exception;

public class UserNameDuplicatedException extends RuntimeException{
    public UserNameDuplicatedException() {

    }

    public UserNameDuplicatedException(String message) {
        super(message);
    }

    public UserNameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}

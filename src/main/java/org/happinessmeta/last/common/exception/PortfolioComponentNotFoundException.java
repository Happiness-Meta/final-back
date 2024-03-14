package org.happinessmeta.last.common.exception;

public class PortfolioComponentNotFoundException extends RuntimeException{

    public PortfolioComponentNotFoundException() {
    }
    public PortfolioComponentNotFoundException(String message) {
        super(message);
    }

    public PortfolioComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

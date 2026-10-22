package com.lumi.core.exception;

public class LumiException extends RuntimeException {
    private int statusCode;

    public LumiException(String message) {
        super(message);
    }

    public LumiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LumiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

package com.mcc_backend.util;

public class CustomCheckedException extends Exception {

    public CustomCheckedException(String message) {
        super(message);
    }

    public CustomCheckedException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public CustomCheckedException() {
        throw new RuntimeException("Something went wrong! Please contact your system administrator.");
    }
}

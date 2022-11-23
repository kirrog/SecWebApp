package ru.itmo.demoproject.exception;

public class BusinessException extends RuntimeException {

    public static final String FEW_VOTES = "Too few votes for skin concept";

    public BusinessException(String message) {
        super(message);
    }
}

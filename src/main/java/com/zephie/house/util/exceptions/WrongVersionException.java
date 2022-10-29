package com.zephie.house.util.exceptions;

public class WrongVersionException extends RuntimeException {
    public WrongVersionException(String message) {
        super(message);
    }
}

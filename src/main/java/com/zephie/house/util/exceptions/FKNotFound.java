package com.zephie.house.util.exceptions;

public class FKNotFound extends RuntimeException {
    public FKNotFound(String message) {
        super(message);
    }
}

package com.zephie.house.util.validators.api;

@FunctionalInterface
public interface IValidator<T> {
    void validate(T t);
}

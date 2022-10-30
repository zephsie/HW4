package com.zephie.house.util.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {
    private static final ThreadLocal<ObjectMapper> mapper = ThreadLocal.withInitial(ObjectMapper::new);

    public static ObjectMapper getObjectMapper() {
        return mapper.get();
    }
}
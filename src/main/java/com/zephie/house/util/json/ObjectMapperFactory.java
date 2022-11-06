package com.zephie.house.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {
    private static final ThreadLocal<ObjectMapper> mapper = ThreadLocal.withInitial(() -> {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    });

    public static ObjectMapper getObjectMapper() {
        return mapper.get();
    }
}
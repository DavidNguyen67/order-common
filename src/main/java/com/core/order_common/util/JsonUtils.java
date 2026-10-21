package com.core.order_common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Common JSON Serialization and Deserialization Utility backed by Jackson.
 */
public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private JsonUtils() {}

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing object to JSON", e);
        }
    }

    public static String toPrettyJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing object to pretty JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> targetClass) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing JSON to " + targetClass.getName(), e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing JSON with TypeReference", e);
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> elementClass) {
        if (json == null || json.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return OBJECT_MAPPER.readValue(
                    json,
                    OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClass)
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing JSON to List<" + elementClass.getName() + ">", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object object) {
        if (object == null) {
            return Collections.emptyMap();
        }
        return OBJECT_MAPPER.convertValue(object, Map.class);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        if (fromValue == null) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(fromValue, toValueType);
    }
}

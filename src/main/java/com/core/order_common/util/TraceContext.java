package com.core.order_common.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * MDC Trace and Correlation Context Helper for end-to-end tracing across REST, Kafka, and Jobs.
 */
public final class TraceContext {

    public static final String TRACE_ID_KEY = "traceId";
    public static final String CORRELATION_ID_KEY = "correlationId";
    public static final String EVENT_ID_KEY = "eventId";
    public static final String ORDER_ID_KEY = "orderId";

    private TraceContext() {}

    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID_KEY);
        if (traceId == null || traceId.isBlank()) {
            traceId = generateTraceId();
            setTraceId(traceId);
        }
        return traceId;
    }

    public static void setTraceId(String traceId) {
        if (traceId != null && !traceId.isBlank()) {
            MDC.put(TRACE_ID_KEY, traceId);
            MDC.put(CORRELATION_ID_KEY, traceId);
        }
    }

    public static void put(String key, String value) {
        if (key != null && value != null) {
            MDC.put(key, value);
        }
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void clear() {
        MDC.clear();
    }

    public static void remove(String key) {
        MDC.remove(key);
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

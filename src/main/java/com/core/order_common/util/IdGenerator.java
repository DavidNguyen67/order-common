package com.core.order_common.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Universal ID generator for Orders, Events, and Transactions.
 */
public final class IdGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private IdGenerator() {}

    public static String nextUuid() {
        return UUID.randomUUID().toString();
    }

    public static String nextSimpleUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String nextEventId() {
        return "EVT-" + nextSimpleUuid();
    }

    public static String nextOrderNumber() {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        int randomNum = 1000 + RANDOM.nextInt(9000);
        return "ORD-" + timestamp + "-" + randomNum;
    }

    public static String nextIdempotencyKey(String prefix, String entityId) {
        return prefix + ":" + entityId + ":" + nextSimpleUuid();
    }
}

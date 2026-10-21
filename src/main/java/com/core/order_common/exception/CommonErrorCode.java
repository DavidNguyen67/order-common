package com.core.order_common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    SUCCESS(200, "Operation succeeded"),
    INVALID_REQUEST(400, "Invalid request parameters"),
    UNAUTHORIZED(401, "Unauthorized access"),
    FORBIDDEN(403, "Access forbidden"),
    NOT_FOUND(404, "Requested resource not found"),
    CONFLICT(409, "Resource conflict"),
    DUPLICATE_EVENT(40901, "Duplicate event detected (Idempotency triggered)"),
    RACE_CONDITION_LOCK_FAILED(40902, "Failed to acquire distributed lock (Race condition detected)"),
    OPTIMISTIC_LOCK_ERROR(40903, "Concurrent modification detected. Please retry"),
    KAFKA_PUBLISH_FAILED(50001, "Failed to publish event to Kafka"),
    KAFKA_CONSUME_RETRY_EXHAUSTED(50002, "Kafka event retry exhausted after max attempts"),
    INTERNAL_SERVER_ERROR(500, "Internal system error occurred");

    private final int code;
    private final String message;
}

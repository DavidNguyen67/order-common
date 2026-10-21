package com.core.order_common;

import com.core.order_common.dto.ApiResponse;
import com.core.order_common.util.DateUtils;
import com.core.order_common.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderCommonUtilsTest {

    @Test
    void testJsonUtils() {
        ApiResponse<String> response = ApiResponse.success("Hello world");
        String json = JsonUtils.toJson(response);
        assertNotNull(json);

        ApiResponse<?> deserialized = JsonUtils.fromJson(json, ApiResponse.class);
        assertEquals(200, deserialized.getCode());
        assertEquals("Hello world", deserialized.getData());

        Map<String, Object> map = JsonUtils.toMap(response);
        assertEquals(200, map.get("code"));
    }

    @Test
    void testDateUtils() {
        String dateStr = "2026-10-21 16:30:00";
        LocalDateTime parsed = DateUtils.parseLocalDateTime(dateStr);
        assertNotNull(parsed);
        assertEquals(2026, parsed.getYear());
        assertEquals(10, parsed.getMonthValue());
        assertEquals(21, parsed.getDayOfMonth());

        String formatted = DateUtils.formatDefault(parsed);
        assertEquals(dateStr, formatted);
    }
}

package com.core.order_common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Universal Date/Time parsing and formatting utilities supporting multiple standard formats.
 */
public final class DateUtils {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Ho_Chi_Minh");
    public static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.ofHours(7);

    public static final String PATTERN_DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_DEFAULT = "yyyy-MM-dd";
    public static final String PATTERN_DATETIME_SLASH = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERN_DATE_SLASH = "dd/MM/yyyy";
    public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static final DateTimeFormatter FORMATTER_DATETIME = DateTimeFormatter.ofPattern(PATTERN_DATETIME_DEFAULT);
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE_DEFAULT);
    public static final DateTimeFormatter FORMATTER_DATETIME_SLASH = DateTimeFormatter.ofPattern(PATTERN_DATETIME_SLASH);
    public static final DateTimeFormatter FORMATTER_DATE_SLASH = DateTimeFormatter.ofPattern(PATTERN_DATE_SLASH);

    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = List.of(
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            DateTimeFormatter.ISO_INSTANT,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            FORMATTER_DATETIME,
            FORMATTER_DATETIME_SLASH,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    );

    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            FORMATTER_DATE,
            FORMATTER_DATE_SLASH,
            DateTimeFormatter.ofPattern("yyyy/MM/dd")
    );

    private DateUtils() {}

    /**
     * Parses a date-time string automatically trying standard date-time formats.
     */
    public static LocalDateTime parseLocalDateTime(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String cleanStr = dateStr.trim();

        // Check if numeric timestamp (seconds or milliseconds)
        if (cleanStr.matches("^\\d+$")) {
            long timestamp = Long.parseLong(cleanStr);
            Instant instant = (cleanStr.length() <= 10) ? Instant.ofEpochSecond(timestamp) : Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, DEFAULT_ZONE_ID);
        }

        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(cleanStr, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try date-only formats and set time to start of day
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(cleanStr, formatter);
                return date.atStartOfDay();
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try Instant parse
        try {
            Instant instant = Instant.parse(cleanStr);
            return LocalDateTime.ofInstant(instant, DEFAULT_ZONE_ID);
        } catch (DateTimeParseException ignored) {
        }

        throw new IllegalArgumentException("Cannot parse date time string: " + dateStr);
    }

    /**
     * Parses an Instant from string or epoch timestamp.
     */
    public static Instant parseInstant(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String cleanStr = dateStr.trim();
        if (cleanStr.matches("^\\d+$")) {
            long ts = Long.parseLong(cleanStr);
            return (cleanStr.length() <= 10) ? Instant.ofEpochSecond(ts) : Instant.ofEpochMilli(ts);
        }
        try {
            return Instant.parse(cleanStr);
        } catch (DateTimeParseException e) {
            LocalDateTime ldt = parseLocalDateTime(cleanStr);
            return ldt.atZone(DEFAULT_ZONE_ID).toInstant();
        }
    }

    public static String format(LocalDateTime ldt, String pattern) {
        if (ldt == null) {
            return null;
        }
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDefault(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }
        return ldt.format(FORMATTER_DATETIME);
    }

    public static String formatIso(Instant instant) {
        if (instant == null) {
            return null;
        }
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }

    public static long toEpochMilli(LocalDateTime ldt) {
        if (ldt == null) {
            return 0L;
        }
        return ldt.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
    }

    public static Instant nowInstant() {
        return Instant.now();
    }

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(DEFAULT_ZONE_ID);
    }
}

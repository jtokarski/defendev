package org.defendev.common.spring6.web;

import org.defendev.common.domain.error.ErrorDto;
import org.defendev.common.domain.exception.DefendevIllegalArgumentException;
import org.defendev.common.domain.exception.DefendevIllegalStateException;
import org.defendev.common.time.TimeUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;
import static java.util.Objects.isNull;



/*
 * Implemented by using java.time.Clock.OffsetClock as a reference.
 *
 */
public class WebTimeTravelClock extends Clock {

    private static final String X_TIME_TRAVEL_TARGET = "X-Time-Travel-Target";

    private static final String X_TIME_TRAVEL_REFERENCE = "X-Time-Travel-Reference";

    private static final String ERROR_CODE_FAILED = "time_travel_failed";

    private static final String ERROR_CODE_INVALID = "time_travel_invalid";

    private final boolean fallbackToSystem;

    private final ZoneId zone;

    public WebTimeTravelClock(boolean fallbackToSystem) {
        this.fallbackToSystem = fallbackToSystem;
        this.zone = TimeUtil.ZULU_ZONE_ID;
    }

    public WebTimeTravelClock(boolean fallbackToSystem, ZoneId zone) {
        this.fallbackToSystem = fallbackToSystem;
        this.zone = zone;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        if (zone.equals(this.zone)) {
            return this;
        }
        return new WebTimeTravelClock(this.fallbackToSystem, zone);
    }

    @Override
    public Instant instant() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attributes) {
            final String targetHeader = attributes.getRequest().getHeader(X_TIME_TRAVEL_TARGET);
            final String referenceHeader = attributes.getRequest().getHeader(X_TIME_TRAVEL_REFERENCE);
            if (fallbackToSystem && isNull(targetHeader) && isNull(referenceHeader)) {
                return Clock.system(zone).instant();
            }
            final ZonedDateTime targetZoned = targetZoned(targetHeader);
            final ZonedDateTime referenceZoned = referenceZoned(referenceHeader);
            final Duration offset = Duration.between(targetZoned, referenceZoned);
            final Instant systemNow = Instant.now(Clock.systemUTC());
            final Instant targetNow = systemNow.plus(offset);
            return targetNow;
        } else {
            throw handleUnexpectedRequestAttributes(requestAttributes);
        }
    }

    private DefendevIllegalStateException handleUnexpectedRequestAttributes(RequestAttributes requestAttributes) {
        if (isNull(requestAttributes)) {
            return new DefendevIllegalStateException(
                new ErrorDto(ERROR_CODE_FAILED, "Time travel failed", "requestAttributes is null", "", null)
            );
        } else {
            return new DefendevIllegalStateException(new ErrorDto(
                ERROR_CODE_FAILED,
                "Time travel failed",
                format("requestAttributes is %s", requestAttributes.getClass().getCanonicalName()),
                "",
                null
            ));
        }
    }

    private ZonedDateTime targetZoned(String targetHeader) {
        if (isNull(targetHeader)) {
            throw new DefendevIllegalArgumentException(new ErrorDto(
                ERROR_CODE_INVALID,
                "Incomplete time-travel request",
                format("Missing header %s", X_TIME_TRAVEL_TARGET),
                "", null)
            );
        }

        try {
             return ZonedDateTime.parse(targetHeader, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException parseException) {
            throw new DefendevIllegalArgumentException(new ErrorDto(
                ERROR_CODE_INVALID,
                "Invalid time-travel request",
                format("Expected ISO-8601 for header %s", X_TIME_TRAVEL_TARGET),
                "", null)
            );
        }
    }

    private ZonedDateTime referenceZoned(String referenceHeader) {
        if (isNull(referenceHeader)) {
            throw new DefendevIllegalArgumentException(new ErrorDto(
                ERROR_CODE_INVALID,
                "Incomplete time-travel request",
                format("Missing header %s", X_TIME_TRAVEL_REFERENCE),
                "", null)
            );
        }

        try {
            return ZonedDateTime.parse(referenceHeader, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException parseException) {
            throw new DefendevIllegalArgumentException(new ErrorDto(
                ERROR_CODE_INVALID,
                "Invalid time-travel request",
                format("Expected ISO-8601 for header %s", X_TIME_TRAVEL_REFERENCE),
                "", null)
            );
        }
    }

}

package org.defendev.common.spring6.web;

import org.defendev.common.domain.error.ErrorDto;
import org.defendev.common.domain.exception.UnclassifiedException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static java.lang.String.format;
import static java.util.Objects.isNull;



/*
 * Implemented by using java.time.Clock.OffsetClock as a reference.
 *
 */
public class WebTimeTravelClock extends Clock {

    private static final String X_TIME_TRAVEL_TARGET = "X-Time-Travel-Target";

    private static final String X_TIME_TRAVEL_REFERENCE = "X-Time-Travel-Reference";

    @Override
    public ZoneId getZone() {
        return null;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attributes) {

            final String targetHeader = attributes.getRequest().getHeader(X_TIME_TRAVEL_TARGET);
            final String referenceHeader = attributes.getRequest().getHeader(X_TIME_TRAVEL_REFERENCE);




            return null;
        } else {
            throw handleUnexpectedRequestAttributes(requestAttributes);
        }
    }

    private UnclassifiedException handleUnexpectedRequestAttributes(RequestAttributes requestAttributes) {
        if (isNull(requestAttributes)) {
            return new UnclassifiedException(
                new ErrorDto("time_travel_failed", "Time travel failed", "requestAttributes is null", "", null)
            );
        } else {
            return new UnclassifiedException(new ErrorDto(
                "time_travel_failed",
                "Time travel failed",
                format("requestAttributes is %s", requestAttributes.getClass().getCanonicalName()),
                "",
                null
            ));
        }
    }

}

package org.defendev.common.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;



public interface IClockManager {

    Clock clockUtc();

    Clock clockEuropeWarsaw();

    Instant nowInstant();

    ZonedDateTime nowZonedUtc();

    ZonedDateTime nowZonedEuropeWarsaw();

}

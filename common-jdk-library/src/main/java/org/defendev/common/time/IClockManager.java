package org.defendev.common.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;



public interface IClockManager {

    Clock clockZulu();

    Clock clockEuropeWarsaw();

    Instant nowInstant();

    ZonedDateTime nowZonedZulu();

    ZonedDateTime nowZonedEuropeWarsaw();

    LocalDateTime nowLocalZulu();

    LocalDateTime nowLocalEuropeWarsaw();

}

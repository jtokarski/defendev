package org.defendev.common.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;



public class ClockManager implements IClockManager {

    private final Clock baseClock;
    
    private final Clock clockZulu;

    private final Clock clockEuropeWarsaw;
    
    public ClockManager(Clock baseClock) {
        this.baseClock = baseClock;
        this.clockZulu = baseClock.withZone(TimeUtil.ZULU_ZONE_ID);
        this.clockEuropeWarsaw = baseClock.withZone(TimeUtil.EUROPE_WARSAW_ZONE_ID);
    }
    
    public static ClockManager system() {
        return new ClockManager(Clock.systemUTC());
    }

    @Override
    public Clock clockZulu() {
        return clockZulu;
    }

    @Override
    public Clock clockEuropeWarsaw() {
        return clockEuropeWarsaw;
    }

    @Override
    public Instant nowInstant() {
        return Instant.now(baseClock);
    }

    @Override
    public ZonedDateTime nowZonedZulu() {
        return ZonedDateTime.now(clockZulu);
    }

    @Override
    public ZonedDateTime nowZonedEuropeWarsaw() {
        return ZonedDateTime.now(clockEuropeWarsaw);
    }

    @Override
    public LocalDateTime nowLocalZulu() {
        return LocalDateTime.now(clockZulu);
    }

    @Override
    public LocalDateTime nowLocalEuropeWarsaw() {
        return LocalDateTime.now(clockEuropeWarsaw);
    }

}

package org.defendev.common.time;

import java.time.Clock;



public class ClockManager implements IClockManager {

    private Clock baseClock;

    public ClockManager(Clock baseClock) {
        this.baseClock = baseClock;
    }

    @Override
    public Clock clockUtc() {
        return baseClock.withZone(TimeUtil.ZULU_ZONE_ID);
    }

    @Override
    public Clock clockEuropeWarsaw() {
        return baseClock.withZone(TimeUtil.EUROPE_WARSAW_ZONE_ID);
    }

}

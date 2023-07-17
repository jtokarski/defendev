package org.defendev.common.time;

import java.time.Clock;



public class ClockManager {

    private Clock baseClock;

    public ClockManager(Clock baseClock) {
        this.baseClock = baseClock;
    }

    public Clock clockUtc() {
        return baseClock.withZone(TimeUtil.ZULU_ZONE_ID);
    }

    public Clock clockEuropeWarsaw() {
        return baseClock.withZone(TimeUtil.EUROPE_WARSAW_ZONE_ID);
    }

}

package org.defendev.common.time;

import java.time.Clock;



public interface IClockManager {

    Clock clockUtc();

    Clock clockEuropeWarsaw();

}

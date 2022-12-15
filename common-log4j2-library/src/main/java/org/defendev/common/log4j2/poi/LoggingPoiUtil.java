package org.defendev.common.log4j2.poi;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;



/**
 * Logging Point-of-Interest (POI) Utility
 *
 * After long deliberation I concluded that Marker(s) in Log4j2 is exactly what can be used
 * for identifying individual code-lines with Logger invocations.
 * If any per-log-invocation Marker seems to granular, it can always be added
 * some generalization by adding parents to it.
 *
 * For a POI to be visible in logs, a marker pattern have to be incorporated into the PatternLayout. Example
 * might be
 *   <Layout type="PatternLayout" pattern="...   %notEmpty{[%markerSimpleName]}   ..." />
 * which displays the marker without the parents like
 *   [poi_1c61b785]
 * We could also choose to display the parent, but this will make it more difficult to keep constant length
 * of POI string.
 * See:
 *   - https://logging.apache.org/log4j/2.x/manual/layouts.html
 *
 */
public class LoggingPoiUtil {

    /**
     * Concise idiom to a POI Marker
     *
     * @param id unique POI identificator. It's the invoker responsibility to keep it unique. Typically
     * this would be a random int.
     * @return the standarized, constant-length POI Marker
     */
    public static Marker poi(int id) {
        /*
         * It's important the marker name is of guaranteed constant length. It will make position-based parsing
         * possible, which would improve performance of log indexers like Splunk.
         *
         */
        return MarkerManager.getMarker(String.format("poi_%1$08x", id));
    }

}
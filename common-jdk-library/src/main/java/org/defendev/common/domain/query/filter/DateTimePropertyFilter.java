package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;
import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



/**
 * See DateFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/packages/ag-grid-community/src/filter/provided/date/iDateFilter.ts
 *
 */
public class DateTimePropertyFilter extends PropertyFilter {

    private final ZonedDateTime dateTimeFrom;

    private final ZonedDateTime dateTimeTo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DateTimePropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") Operator operator,
        @JsonProperty("dateTimeFrom") ZonedDateTime dateTimeFrom,
        @JsonProperty("dateTimeTo") ZonedDateTime dateTimeTo
    ) {
        super(property, operator);
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }

    public ZonedDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public ZonedDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public LocalDateTime getDateTimeFromZulu() {
        return dateTimeFrom.withZoneSameInstant(ZULU_ZONE_ID).toLocalDateTime();
    }

    public LocalDateTime getDateTimeToZulu() {
        return dateTimeTo.withZoneSameInstant(ZULU_ZONE_ID).toLocalDateTime();
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for DateTimePropertyFilter");
        specRequireNonNull(operator, "operator is required for DateTimePropertyFilter");
        switch (operator) {
            /*
             * Since DateTimePropertyFilter operates on ZonedDateTime "instants" we intentionally do not want
             * to support 'equals' operator. If needed the 'equals' date (no time) filter should be
             * translated on frontend to 'inRange' of 24h.
             *
             * "Dijkstra argues that [a, b) is the best way to represent an range boundary"
             * This is why:
             *   - 'lessThan' is without 'OrEqual'
             *   - 'greaterThanOrEqual' is with 'OrEqual'
             */
            case lessThan:
                specRequireNonNull(dateTimeFrom,
                    "dateTimeFrom is required for lessThan operator in DateTimePropertyFilter");
                return;
            case greaterThanOrEqual:
                specRequireNonNull(dateTimeFrom,
                    "dateTimeFrom is required for greaterThanOrEqual operator in DateTimePropertyFilter");
                return;
            case inRange:
                specRequireNonNull(dateTimeFrom,
                    "dateTimeFrom is required for inRange operator in DateTimePropertyFilter");
                return;
            case blank:
            case notBlank:
                return;
            default:
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for DateTimePropertyFilter",
                    "Operator " + operator.name() + " not supported for DateTimePropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
        }
    }

}

package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;


/**
 * See DateFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/grid-community-modules/core/src/ts/filter/provided/date/dateFilter.ts
 *
 */
public class DateTimePropertyFilter extends PropertyFilter {

    private final ZonedDateTime value;

    private final ZonedDateTime valueTo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DateTimePropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") Operator operator,
        @JsonProperty("value") ZonedDateTime value,
        @JsonProperty("valueTo") ZonedDateTime valueTo
    ) {
        super(property, operator);
        this.value = value;
        this.valueTo = valueTo;
    }

    public ZonedDateTime getValue() {
        return value;
    }

    public ZonedDateTime getValueTo() {
        return valueTo;
    }
}

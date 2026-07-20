package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static java.lang.String.format;
import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;
import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



/**
 * See DateFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/packages/ag-grid-community/src/filter/provided/date/iDateFilter.ts
 *
 */
public class DateTimePropertyFilter extends PropertyFilter<DateTimeOperator> {

    private final ZonedDateTime value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DateTimePropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") DateTimeOperator operator,
        @JsonProperty("value") ZonedDateTime value
    ) {
        super(property, operator);
        this.value = value;
    }

    public ZonedDateTime getValue() {
        return value;
    }

    public LocalDateTime getValueZulu() {
        return value.withZoneSameInstant(ZULU_ZONE_ID).toLocalDateTime();
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for DateTimePropertyFilter");
        specRequireNonNull(operator, "operator is required for DateTimePropertyFilter");
        switch (operator) {
            case equals, notEqual, after, afterOrEqual, before, beforeOrEqual -> specRequireNonNull(
                    value,
                    format("value is required for %s operator in DateTimePropertyFilter", operator.name())
                );
            case empty, notEmpty -> { }
            default -> {
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for DateTimePropertyFilter",
                    "Operator " + operator.name() + " not supported for DateTimePropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
            }
        }
    }

}

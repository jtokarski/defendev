package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/**
 * See NumberFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/grid-community-modules/core/src/ts/filter/provided/number/numberFilter.ts
 *
 */
public class NumberPropertyFilter extends PropertyFilter {

    private final Number value;

    private final Number valueTo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NumberPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") Operator operator,
        @JsonProperty("value") Number value,
        @JsonProperty("valueTo") Number valueTo
    ) {
        super(property, operator);
        this.value = value;
        this.valueTo = valueTo;
    }

    public Number getValue() {
        return value;
    }

    public Number getValueTo() {
        return valueTo;
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for NumberPropertyFilter");
        specRequireNonNull(operator, "operator is required for NumberPropertyFilter");
        switch (operator) {
            case equals:
                specRequireNonNull(value, "value is required for equals operator in NumberPropertyFilter");
                return;
            case notEqual:
                specRequireNonNull(value, "value is required for notEqual operator in NumberPropertyFilter");
                return;
            case lessThan:
                specRequireNonNull(value, "value is required for lessThan operator in NumberPropertyFilter");
                return;
            case lessThanOrEqual:
                specRequireNonNull(value,
                    "value is required for lessThanOrEqual operator in NumberPropertyFilter");
                return;
            case greaterThan:
                specRequireNonNull(value,
                    "value is required for greaterThan operator in NumberPropertyFilter");
                return;
            case greaterThanOrEqual:
                specRequireNonNull(value,
                    "value is required for greaterThanOrEqual operator in NumberPropertyFilter");
                return;
            case inRange:
                specRequireNonNull(value,
                    "value is required for inRange operator in NumberPropertyFilter");
                specRequireNonNull(valueTo,
                    "valueTo is required for inRange operator in NumberPropertyFilter");
                return;
            case blank:
            case notBlank:
                return;
            default:
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for NumberPropertyFilter",
                    "Operator " + operator.name() + " not supported for NumberPropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
        }
    }

}

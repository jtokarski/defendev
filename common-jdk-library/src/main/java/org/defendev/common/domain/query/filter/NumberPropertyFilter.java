package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/**
 * See NumberFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/packages/ag-grid-community/src/filter/provided/number/iNumberFilter.ts
 *
 */
public class NumberPropertyFilter extends PropertyFilter {

    private final Number filter;

    private final Number filterTo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NumberPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") Operator operator,
        @JsonProperty("filter") Number filter,
        @JsonProperty("filterTo") Number filterTo
    ) {
        super(property, operator);
        this.filter = filter;
        this.filterTo = filterTo;
    }

    public Number getFilter() {
        return filter;
    }

    public Number getFilterTo() {
        return filterTo;
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for NumberPropertyFilter");
        specRequireNonNull(operator, "operator is required for NumberPropertyFilter");
        switch (operator) {
            case equals:
                specRequireNonNull(filter, "value is required for equals operator in NumberPropertyFilter");
                return;
            case notEqual:
                specRequireNonNull(filter, "value is required for notEqual operator in NumberPropertyFilter");
                return;
            case lessThan:
                specRequireNonNull(filter, "value is required for lessThan operator in NumberPropertyFilter");
                return;
            case lessThanOrEqual:
                specRequireNonNull(filter,
                    "value is required for lessThanOrEqual operator in NumberPropertyFilter");
                return;
            case greaterThan:
                specRequireNonNull(filter,
                    "value is required for greaterThan operator in NumberPropertyFilter");
                return;
            case greaterThanOrEqual:
                specRequireNonNull(filter,
                    "value is required for greaterThanOrEqual operator in NumberPropertyFilter");
                return;
            case inRange:
                specRequireNonNull(filter,
                    "value is required for inRange operator in NumberPropertyFilter");
                specRequireNonNull(filterTo,
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

package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static java.lang.String.format;
import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/**
 * See NumberFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/packages/ag-grid-community/src/filter/provided/number/iNumberFilter.ts
 *
 */
public class NumberPropertyFilter extends PropertyFilter<NumberOperator> {

    private final Number value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NumberPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") NumberOperator operator,
        @JsonProperty("value") Number value
    ) {
        super(property, operator);
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for NumberPropertyFilter");
        specRequireNonNull(operator, "operator is required for NumberPropertyFilter");
        switch (operator) {
            case empty, notEmpty -> { }
            case equals, notEqual, lessThan, lessThanOrEqual, greaterThan, greaterThanOrEqual -> specRequireNonNull(
                value,
                format("value is required for %s operator in NumberPropertyFilter", operator.name())
            );
            default -> {
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for NumberPropertyFilter",
                    "Operator " + operator.name() + " not supported for NumberPropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
            }
        }
    }

}

package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/**
 * See TextFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/grid-community-modules/core/src/ts/filter/provided/text/textFilter.ts
 *
 */
public class TextPropertyFilter extends PropertyFilter {

    private final String value;

    private final String valueTo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TextPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") Operator operator,
        @JsonProperty("value") String value,
        @JsonProperty("valueTo") String valueTo
    ) {
        super(property, operator);
        this.value = value;
        this.valueTo = valueTo;
    }

    public String getValue() {
        return value;
    }

    public String getValueTo() {
        return valueTo;
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for TextPropertyFilter");
        specRequireNonNull(operator, "operator is required for TextPropertyFilter");
        switch (operator) {
            case blank:
            case notBlank:
                return;
            case contains:
                specRequireNonNull(value, "value is required for contains operator in TextPropertyFilter");
                return;
            case notContains:
                specRequireNonNull(value, "value is required for notContains operator in TextPropertyFilter");
                return;
            default:
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for TextPropertyFilter",
                    "Operator " + operator.name() + " not supported for TextPropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
        }
    }

}

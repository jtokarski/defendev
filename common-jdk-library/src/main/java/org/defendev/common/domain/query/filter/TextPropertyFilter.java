package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static java.lang.String.format;
import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/**
 * See TextFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/packages/ag-grid-community/src/filter/provided/text/iTextFilter.ts
 *
 */
public class TextPropertyFilter extends PropertyFilter<TextOperator> {

    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TextPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") TextOperator operator,
        @JsonProperty("value") String value
    ) {
        super(property, operator);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for TextPropertyFilter");
        specRequireNonNull(operator, "operator is required for TextPropertyFilter");
        switch (operator) {
            case equals, notEqual, contains, notContains, startsWith, endsWith -> specRequireNonNull(
                    value,
                    format("value is required for %s operator in TextPropertyFilter", operator.name())
                );
            case blank, notBlank -> { }
            default -> {
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for TextPropertyFilter",
                    "Operator " + operator.name() + " not supported for TextPropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
            }
        }
    }

}

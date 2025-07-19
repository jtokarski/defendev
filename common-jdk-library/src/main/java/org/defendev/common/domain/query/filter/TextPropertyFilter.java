package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/**
 * See TextFilterModel in
 *   https://github.com/ag-grid/ag-grid/blob/latest/packages/ag-grid-community/src/filter/provided/text/iTextFilter.ts
 *
 */
public class TextPropertyFilter extends PropertyFilter {

    private final String filter;

    private final String filterTo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TextPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") Operator operator,
        @JsonProperty("filter") String filter,
        @JsonProperty("filterTo") String filterTo
    ) {
        super(property, operator);
        this.filter = filter;
        this.filterTo = filterTo;
    }

    public String getFilter() {
        return filter;
    }

    public String getFilterTo() {
        return filterTo;
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
                specRequireNonNull(filter, "value is required for contains operator in TextPropertyFilter");
                return;
            case notContains:
                specRequireNonNull(filter, "value is required for notContains operator in TextPropertyFilter");
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

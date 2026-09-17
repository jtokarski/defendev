package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import java.util.Set;

import static java.lang.String.format;
import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;
import static org.defendev.common.domain.query.filter.Filters.specRequireNotEmpty;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



public class ExternalIdSetPropertyFilter extends PropertyFilter<ExternalIdSetOperator> {

    private final Set<String> value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ExternalIdSetPropertyFilter(
        @JsonProperty("property") String property,
        @JsonProperty("operator") ExternalIdSetOperator operator,
        @JsonProperty("value") Set<String> value
    ) {
        super(property, operator);
        this.value = value;
    }

    public Set<String> getValue() {
        return value;
    }

    @Override
    public void validate() throws QueryFailedException {
        specRequireNonNull(property, "property is required for ExternalIdSetPropertyFilter");
        specRequireNonNull(operator, "operator is required for ExternalIdSetPropertyFilter");
        switch (operator) {
            case containsAll, containsAny, notContainsAll, notContainsAny -> specRequireNotEmpty(
                value,
                format("Non-empty value is required for %s operator in ExternalIdSetPropertyFilter", operator.name())
            );
            case hasAny, notHasAny -> { }
            default -> {
                final QueryResult<Void> queryResult = new QueryResult<>(
                    REQUEST_INVALID,
                    "Operator " + operator.name() + " not supported for ExternalIdSetPropertyFilter",
                    "Operator " + operator.name() + " not supported for ExternalIdSetPropertyFilter",
                    null);
                throw new QueryFailedException(queryResult);
            }
        }
    }

}

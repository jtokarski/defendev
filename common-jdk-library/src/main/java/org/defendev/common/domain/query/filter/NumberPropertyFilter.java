package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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
}

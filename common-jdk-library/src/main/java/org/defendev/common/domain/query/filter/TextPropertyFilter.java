package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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

}

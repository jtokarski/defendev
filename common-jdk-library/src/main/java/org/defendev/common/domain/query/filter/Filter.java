package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Filter {

    private List<NumberPropertyFilter> numberPropertyFilters;

    private List<TextPropertyFilter> textPropertyFilters;

    private List<DateTimePropertyFilter> dateTimePropertyFilters;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Filter(
        @JsonProperty("numberPropertyFilters") List<NumberPropertyFilter> numberPropertyFilters,
        @JsonProperty("textPropertyFilters") List<TextPropertyFilter> textPropertyFilters,
        @JsonProperty("dateTimePropertyFilters") List<DateTimePropertyFilter> dateTimePropertyFilters
    ) {
        this.numberPropertyFilters = numberPropertyFilters;
        this.textPropertyFilters = textPropertyFilters;
        this.dateTimePropertyFilters = dateTimePropertyFilters;
    }

    public List<NumberPropertyFilter> getNumberPropertyFilters() {
        return numberPropertyFilters;
    }

    public List<TextPropertyFilter> getTextPropertyFilters() {
        return textPropertyFilters;
    }

    public List<DateTimePropertyFilter> getDateTimePropertyFilters() {
        return dateTimePropertyFilters;
    }
}

package org.defendev.common.domain.query.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;

import java.util.List;

import static java.util.Objects.nonNull;



public class Filter {

    private List<NumberPropertyFilter> numberPropertyFilters;

    private List<TextPropertyFilter> textPropertyFilters;

    private List<DateTimePropertyFilter> dateTimePropertyFilters;

    private List<ExternalIdSetPropertyFilter> externalIdSetPropertyFilters;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Filter(
        @JsonProperty("numberPropertyFilters") List<NumberPropertyFilter> numberPropertyFilters,
        @JsonProperty("textPropertyFilters") List<TextPropertyFilter> textPropertyFilters,
        @JsonProperty("dateTimePropertyFilters") List<DateTimePropertyFilter> dateTimePropertyFilters,
        @JsonProperty("externalIdSetPropertyFilters") List<ExternalIdSetPropertyFilter> externalIdSetPropertyFilters
    ) {
        this.numberPropertyFilters = numberPropertyFilters;
        this.textPropertyFilters = textPropertyFilters;
        this.dateTimePropertyFilters = dateTimePropertyFilters;
        this.externalIdSetPropertyFilters = externalIdSetPropertyFilters;
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

    public List<ExternalIdSetPropertyFilter> getExternalIdSetPropertyFilters() {
        return externalIdSetPropertyFilters;
    }

    public void validate() throws QueryFailedException {
        if (nonNull(numberPropertyFilters)) {
            for (final NumberPropertyFilter numberPropertyFilter : numberPropertyFilters) {
                numberPropertyFilter.validate();
            }
        }
        if (nonNull(textPropertyFilters)) {
            for (final TextPropertyFilter textPropertyFilter : textPropertyFilters) {
                textPropertyFilter.validate();
            }
        }
        if (nonNull(dateTimePropertyFilters)) {
            for (final DateTimePropertyFilter dateTimePropertyFilter : dateTimePropertyFilters) {
                dateTimePropertyFilter.validate();
            }
        }
        if (nonNull(externalIdSetPropertyFilters)) {
            for (final ExternalIdSetPropertyFilter externalIdSetPropertyFilter : externalIdSetPropertyFilters) {
                externalIdSetPropertyFilter.validate();
            }
        }
    }

}

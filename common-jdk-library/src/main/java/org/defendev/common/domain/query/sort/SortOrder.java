package org.defendev.common.domain.query.sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.exception.QueryFailedException;

import java.util.Objects;

import static org.defendev.common.domain.query.filter.Filters.specRequireNonNull;



public class SortOrder {

    private final SortDirection direction;

    private final String property;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SortOrder(
        @JsonProperty("direction") SortDirection direction,
        @JsonProperty("property") String property
    ) {
        this.direction = direction;
        this.property = property;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }

    public void validate() throws QueryFailedException {
        specRequireNonNull(direction, "direction is required for SortOrder");
        specRequireNonNull(property, "property is required for SortOrder");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortOrder sortOrder = (SortOrder) o;
        return direction == sortOrder.direction && Objects.equals(property, sortOrder.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, property);
    }

}

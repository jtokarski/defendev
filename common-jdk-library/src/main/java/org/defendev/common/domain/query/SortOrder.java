package org.defendev.common.domain.query;

import java.util.Objects;


public class SortOrder {

    private final SortDirection direction;

    private final String property;

    public SortOrder(SortDirection direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
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

package org.defendev.common.domain.query;


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

}

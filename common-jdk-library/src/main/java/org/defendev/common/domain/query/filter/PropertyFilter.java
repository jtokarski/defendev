package org.defendev.common.domain.query.filter;



public abstract class PropertyFilter {

    private final String property;

    private final Operator operator;

    public PropertyFilter(String property, Operator operator) {
        this.property = property;
        this.operator = operator;
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }
}

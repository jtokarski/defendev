package org.defendev.common.domain.query.filter;

import org.defendev.common.domain.exception.QueryFailedException;



public abstract class PropertyFilter<OP extends Enum<OP>> {

    protected final String property;

    protected final OP operator;

    public PropertyFilter(String property, OP operator) {
        this.property = property;
        this.operator = operator;
    }

    public String getProperty() {
        return property;
    }

    public OP getOperator() {
        return operator;
    }

    public abstract void validate() throws QueryFailedException;

}

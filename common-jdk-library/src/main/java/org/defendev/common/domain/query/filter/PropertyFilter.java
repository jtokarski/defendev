package org.defendev.common.domain.query.filter;

import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;


public abstract class PropertyFilter {

    protected final String property;

    protected final Operator operator;

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

    public abstract void validate() throws QueryFailedException;

}

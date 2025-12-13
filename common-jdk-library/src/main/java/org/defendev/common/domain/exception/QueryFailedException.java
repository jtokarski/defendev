package org.defendev.common.domain.exception;

import org.defendev.common.domain.query.result.QueryResult;



public class QueryFailedException extends RuntimeException implements IdentifiableException {

    private QueryResult<?> queryResult;

    private final String exceptionId;

    public QueryFailedException(QueryResult<?> queryResult) {
        this(queryResult, false);
    }

    public QueryFailedException(QueryResult<?> queryResult, boolean doGenerateExceptionId) {
        this.queryResult = queryResult;
        if (doGenerateExceptionId) {
            exceptionId = IdentifiableException.generateExceptionId();
        } else {
            exceptionId = "";
        }
    }

    @Override
    public String getExceptionId() {
        return exceptionId;
    }

    public QueryResult<?> getQueryResult() {
        return queryResult;
    }

}

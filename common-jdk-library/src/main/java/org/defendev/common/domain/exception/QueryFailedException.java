package org.defendev.common.domain.exception;

import org.defendev.common.domain.query.result.QueryResult;



public class QueryFailedException extends RuntimeException {

    private QueryResult<?> queryResult;

    public QueryFailedException(QueryResult<?> queryResult) {
        this.queryResult = queryResult;
    }

    public QueryResult<?> getQueryResult() {
        return queryResult;
    }

}

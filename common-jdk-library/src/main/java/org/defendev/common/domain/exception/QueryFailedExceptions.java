package org.defendev.common.domain.exception;

import org.defendev.common.domain.query.filter.Operator;
import org.defendev.common.domain.query.result.QueryResult;

import static java.lang.String.format;



public class QueryFailedExceptions {

    public static QueryFailedException filterOperatorNotSupported(Operator operator) {
        return new QueryFailedException(new QueryResult<Void>(
            QueryResult.Status.REQUEST_INVALID,
            format("Operator not supported [%s]", operator),
            "Frontend-Backend communication problem.",
            null
        ));
    }

    public static QueryFailedException sortPropertyNotSupported(String property) {
        return new QueryFailedException(new QueryResult<Void>(
            QueryResult.Status.REQUEST_INVALID,
            format("Property not supported [%s]", property),
            "Frontend-Backend communication problem.",
            null
        ));
    }

}

package org.defendev.common.domain.query.filter;

import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;

import static java.util.Objects.isNull;
import static org.defendev.common.domain.query.result.QueryResult.Status.REQUEST_INVALID;



/*
 * Method naming inspired by java.util.Objects methods like requireNonNull(T obj, String message)
 * but throwing QueryFailedException.
 *
 */
public class Filters {

    public static <T> T specRequireNonNull(T obj, String message) {
        if (isNull(obj)) {
            throw new QueryFailedException(new QueryResult<Void>(REQUEST_INVALID, message, message, null));
        }
        return obj;
    }

}

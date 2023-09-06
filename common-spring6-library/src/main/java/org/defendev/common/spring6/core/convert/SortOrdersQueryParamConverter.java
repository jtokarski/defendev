package org.defendev.common.spring6.core.convert;

import org.apache.commons.lang3.StringUtils;
import org.defendev.common.domain.exception.QueryFailedException;
import org.defendev.common.domain.query.result.QueryResult;
import org.springframework.core.convert.converter.Converter;
import org.defendev.common.domain.query.sort.SortOrder;
import org.defendev.common.domain.query.sort.SortDirection;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;


/**
 *
 * Type converter that parses a string containing a specification of sort orders
 * to a list of structured objects. It's intended primarily for use in URL query params.
 * The form of parsable specification is like:
 * <pre>
 * lastName:ASC,firstName:DESC,createdAt:ASC
 * </pre>
 *
 *
 */
public class SortOrdersQueryParamConverter implements Converter<String, List<SortOrder>> {

    @Override
    public List<SortOrder> convert(String source) {

        final String[] orderStrings = StringUtils.split(source, ",");

        if (isNull(orderStrings)) {
            return List.of();
        }

        return Stream.of(orderStrings).map(
            (String orderString) -> {
                final String[] propertyAndDirection = StringUtils.split(orderString, ":", 2);
                if (isNull(propertyAndDirection) || 2 != propertyAndDirection.length) {
                    throw new QueryFailedException(new QueryResult(QueryResult.Status.REQUEST_INVALID,
                        "Invalid sort specified", null, null));
                }
                return new SortOrder(SortDirection.valueOf(propertyAndDirection[1]), propertyAndDirection[0]);
            }
        ).collect(Collectors.toList());

    }


}

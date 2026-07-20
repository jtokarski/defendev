package org.defendev.common.spring6.data.jpa.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.defendev.common.domain.query.filter.DateTimePropertyFilter;
import org.defendev.common.domain.query.filter.Filter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.defendev.common.domain.exception.QueryFailedExceptions.filterOperatorNotSupported;
import static org.defendev.common.stream.Streams.stream;
import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



public class PredicateSpecs {

    public static Predicate applyStandardDateTimePropertyFilter(Predicate predicate, CriteriaBuilder criteriaBuilder,
                                                                 Filter filter, String propertyName,
                                                                 Path<LocalDateTime> entityPath) {
        final List<DateTimePropertyFilter> dateTimePropertyFilters = filter.getDateTimePropertyFilters();

        final List<DateTimePropertyFilter> propertyFilters = stream(dateTimePropertyFilters)
            .filter(propFilter -> propertyName.equals(propFilter.getProperty()))
            .toList();

        for (DateTimePropertyFilter propertyFilter : propertyFilters) {
            propertyFilter.validate();
            final ZonedDateTime dateTimeValue = propertyFilter.getValue();
            final LocalDateTime dateTimeValueZulu = dateTimeValue.withZoneSameInstant(ZULU_ZONE_ID).toLocalDateTime();
            switch (propertyFilter.getOperator()) {
                case before -> predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThan(entityPath, dateTimeValueZulu)
                    );
                case afterOrEqual -> predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.greaterThanOrEqualTo(entityPath, dateTimeValueZulu)
                    );
                default -> throw filterOperatorNotSupported(propertyFilter.getOperator().name());
            }
        }

        return predicate;
    }

}

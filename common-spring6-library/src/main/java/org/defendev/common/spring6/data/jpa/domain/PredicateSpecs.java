package org.defendev.common.spring6.data.jpa.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.defendev.common.domain.query.filter.DateTimePropertyFilter;
import org.defendev.common.domain.query.filter.Filter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.defendev.common.domain.exception.QueryFailedExceptions.filterOperatorNotSupported;
import static org.defendev.common.stream.Streams.stream;
import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



public class PredicateSpecs {

    public static Predicate applyStandardDateTimePropertyFilter(Predicate predicate, CriteriaBuilder criteriaBuilder,
                                                                 Filter filter, String propertyName,
                                                                 Path<LocalDateTime> entityPath) {
        final List<DateTimePropertyFilter> dateTimePropertyFilters = filter.getDateTimePropertyFilters();

        final Optional<DateTimePropertyFilter> createZonedOptional = stream(dateTimePropertyFilters)
            .filter(propFilter -> propertyName.equals(propFilter.getProperty()))
            .findAny();
        if (createZonedOptional.isPresent()) {
            final DateTimePropertyFilter createZonedFilter = createZonedOptional.get();
            createZonedFilter.validate();
            final ZonedDateTime dateTimeFrom = createZonedFilter.getDateTimeFrom();
            final LocalDateTime dateTimeFromZulu = dateTimeFrom.withZoneSameInstant(ZULU_ZONE_ID).toLocalDateTime();
            switch (createZonedFilter.getOperator()) {
                case lessThan:
                    predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThan(entityPath, dateTimeFromZulu)
                    );
                    break;
                case greaterThanOrEqual:
                    predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.greaterThanOrEqualTo(entityPath, dateTimeFromZulu)
                    );
                    break;
                case inRange:
                    final ZonedDateTime dateTimeTo = createZonedFilter.getDateTimeTo();
                    final LocalDateTime dateTimeToZulu = dateTimeTo.withZoneSameInstant(ZULU_ZONE_ID).toLocalDateTime();
                    predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.greaterThanOrEqualTo(entityPath, dateTimeFromZulu),
                        criteriaBuilder.lessThan(entityPath, dateTimeToZulu)
                    );
                    break;
                default:
                    throw filterOperatorNotSupported(createZonedFilter.getOperator());
            }
        }
        return predicate;
    }

}

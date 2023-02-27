package org.defendev.common.spring6.data.jpa.domain;

import org.defendev.common.domain.query.sort.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;




public interface PageableSpecification<T> {

    Map<SortDirection, Direction> TO_SPRINGDATA_DIRECTION = Map.of(
        SortDirection.asc, Direction.ASC,
        SortDirection.desc, Direction.DESC
    );

    static Sort jpaSortConjunct(List<JpaSort> sorts) {
        if (isNull(sorts) || sorts.isEmpty()) {
            throw new IllegalStateException("In order to conjunct, sorts list must not be empty");
        }
        Sort result = sorts.get(0);
        for (int i = 1; i < sorts.size(); i += 1) {
            result = result.and(sorts.get(i));
        }
        return result;
    }

    JpaSort getDefaultSort();

    Pageable toPageable();

}

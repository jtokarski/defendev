package org.defendev.common.spring6.data.jpa.domain;

import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.sort.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;




public abstract class PageableSpecification<T extends Query> {

    public static final Map<SortDirection, Direction> TO_SPRINGDATA_DIRECTION = Map.of(
        SortDirection.asc, Direction.ASC,
        SortDirection.desc, Direction.DESC
    );

    protected final T query;

    public PageableSpecification(T query) {
        this.query = query;
    }

    public abstract JpaSort getDefaultSort();

    public abstract Pageable toPageable();

    public Sort conjunctWithDefaultSort(List<JpaSort> sorts) {
        final List<JpaSort> sortsWithDefault = new ArrayList<>(isNull(sorts) ? List.of() : sorts);
        sortsWithDefault.add(getDefaultSort());
        Sort result = sortsWithDefault.get(0);
        for (int i = 1; i < sortsWithDefault.size(); i += 1) {
            result = result.and(sortsWithDefault.get(i));
        }
        return result;
    }

}

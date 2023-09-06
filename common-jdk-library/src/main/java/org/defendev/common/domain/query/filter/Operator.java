package org.defendev.common.domain.query.filter;

/**
 * Enumerates operators that construct filter expressions in entity queries
 *
 * @see <a href=
 * "https://github.com/ag-grid/ag-grid/blob/latest/grid-community-modules/core/src/ts/filter/provided/simpleFilter.ts">
 * ISimpleFilterModelType</a>
 */
public enum Operator {
    empty,
    equals,
    notEqual,
    lessThan,
    lessThanOrEqual,
    greaterThan,
    greaterThanOrEqual,
    inRange,
    contains,
    notContains,
    startsWith,
    endsWith,
    blank,
    notBlank
}

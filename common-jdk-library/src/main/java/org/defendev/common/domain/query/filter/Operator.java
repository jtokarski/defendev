package org.defendev.common.domain.query.filter;

/**
 *
 * See ISimpleFilterModelType in
 *   https://github.com/ag-grid/ag-grid/blob/latest/grid-community-modules/core/src/ts/filter/provided/simpleFilter.ts
 *
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

package org.defendev.common.domain.query.filter;



public enum NumberOperator {
    empty,
    notEmpty,
    equals,
    notEqual,
    lessThan,
    lessThanOrEqual,
    greaterThan,
    greaterThanOrEqual,
    // inRange, // As of 2026-07-02 I'm intentionally removing the 'inRange' operator. Why?
                //   - it can be easily replaced by combination of (greaterThanOrEqual, lessThan). It
                //     makes it more expressive (no hidden Dijkstra [a, b) semantics)
                //   - it eliminates the need for having filterTo property in PropertyFilter classes, also
                //     making them more expressive
                //   - in the end makes my design more publicly acceptable
                //
}

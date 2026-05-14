package org.defendev.common.domain.query;



/**
 *  Implemented by domain queries which specify literal search string for exact match (inclusive or exclusive).
 *
 */
public interface QuerySearchString {

    String getSearchStringInclusive();

    String getSearchStringExclusive();

}

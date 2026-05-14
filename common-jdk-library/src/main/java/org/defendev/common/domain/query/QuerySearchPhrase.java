package org.defendev.common.domain.query;



/**
 * Implemented by domain queries which specify a non-literal search phrase, to be handled by advanced test
 * indexing and search engines.
 *
 */
public interface QuerySearchPhrase {

    String getSearchPhrase();

}

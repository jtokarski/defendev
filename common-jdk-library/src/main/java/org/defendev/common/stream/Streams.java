package org.defendev.common.stream;

import java.util.stream.Stream;
import java.util.Collection;

import static java.util.Objects.isNull;



public class Streams {

    public static <E> Stream<E> stream(Collection<E> collection) {
        return isNull(collection) ?
            Stream.<E>empty()
            : collection.stream();
    }

    /* TODO: possibly consider variant with Iterable  */

}

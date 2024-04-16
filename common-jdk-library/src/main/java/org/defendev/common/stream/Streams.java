package org.defendev.common.stream;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Stream;

import static java.util.Objects.isNull;



public class Streams {

    public static <E> Stream<E> stream(Collection<E> collection) {
        return isNull(collection) ?
            Stream.<E>empty()
            : collection.stream();
    }

    public static <E> Stream<E> stream(Enumeration<E> enumeration) {
        return isNull(enumeration) ?
            Stream.<E>empty()
            : Collections.list(enumeration).stream();
    }

    /* TODO: possibly consider variant with Iterable  */

}

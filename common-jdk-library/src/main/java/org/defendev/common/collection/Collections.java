package org.defendev.common.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Consumer;
import static java.util.Objects.requireNonNull;



public class Collections {

    /**
     * Runs an action specified by {@code consumer} on each collection element that matches given {@code predicate}
     * and removes every matched element.
     *
     * @param collection the collection to mutate
     * @param predicate decides whether the element should be actioned and removed
     * @param consumer specified the action (side-effect) on removed element
     * @param <E> type of collection element
     *
     * @return the count of actioned and removed elements
     *
     * @throws NullPointerException if any argument is null
     */
    public static <E> int removeIfDo(Collection<E> collection,
                                     Predicate<? super E> predicate, 
                                     Consumer<? super E> consumer) {
        requireNonNull(collection, "requireNonNull");
        requireNonNull(predicate, "predicate");
        requireNonNull(consumer, "consumer");
        
        int removedCount = 0;
        final Iterator<E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final E element = iterator.next();
            if (predicate.test(element)) {
                consumer.accept(element);
                iterator.remove();
                removedCount += 1;
            }
        }
        return removedCount;
    }

}

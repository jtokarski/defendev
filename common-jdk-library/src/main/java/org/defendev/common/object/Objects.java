package org.defendev.common.object;

import static java.util.Objects.nonNull;



public class Objects {

    /**
     * @return {@code true} if the arguments are equal to each other and {@code false} otherwise.
     * Contrary to {@link java.util.Objects#equals(Object, Object)}, if both arguments are {@code null},
     * {@code false} is returned
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @see java.util.Objects#equals(Object, Object)
     */
    public static boolean equalsIfNonNull(Object a, Object b) {
        return nonNull(a) && nonNull(b) && a.equals(b);
    }

}

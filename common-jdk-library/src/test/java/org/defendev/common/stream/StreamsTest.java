package org.defendev.common.stream;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.defendev.common.stream.Streams.stream;



public class StreamsTest {

    @Test
    public void arrayToStreamTest() {
        // given
        final String[] nullArray = null;
        final String[] emptyArray = new String[0];
        final String[] nonEmptyArray = new String[] { "a", "b", "c" };

        // when
        final List<String> listFromNullArray = stream(nullArray).toList();
        final List<String> listFromEmptyArray = stream(emptyArray).toList();
        final List<String> listFromNonEmptyArray = stream(nonEmptyArray).toList();

        // then
        assertThat(listFromNullArray).isNotNull().isEmpty();
        assertThat(listFromEmptyArray).isNotNull().isEmpty();
        assertThat(listFromNonEmptyArray).isNotNull().contains("a", "b", "b");
    }

}

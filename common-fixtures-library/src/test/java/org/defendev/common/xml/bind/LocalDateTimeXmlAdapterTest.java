package org.defendev.common.xml.bind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;



public class LocalDateTimeXmlAdapterTest {

    private LocalDateTimeXmlAdapter underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocalDateTimeXmlAdapter();
    }

    @Test
    public void shouldUnmarshalCorrectValue1() throws Exception {
        // given
        final String xmlValue = "2016-01-01T17:41:00";

        // when
        final LocalDateTime unmarshalledValue = underTest.unmarshal(xmlValue);

        // then
        assertThat(unmarshalledValue)
            .isEqualTo(LocalDateTime.of(2016, Month.JANUARY, 1, 17, 41));
    }

    @Test
    public void shouldUnmarshalCorrectValue2() throws Exception {
        // given
        final String xmlValue = "2022-02-17T21:58:59.999";

        // when
        final LocalDateTime unmarshalledValue = underTest.unmarshal(xmlValue);

        // then
        assertThat(unmarshalledValue)
            .isEqualTo(LocalDateTime.of(2022, Month.FEBRUARY, 17, 21, 58, 59, 999_000_000));
    }

    @Test
    public void shouldUnmarshalBlankAsNull() throws Exception {
        // given
        final String xmlValue = "   ";

        // when
        final LocalDateTime unmarshalledValue = underTest.unmarshal(xmlValue);

        // then
        assertThat(unmarshalledValue).isNull();
    }

    @Test
    public void shouldUnmarshalEmptyAsNull() throws Exception {
        // given
        final String xmlValue = "";

        // when
        final LocalDateTime unmarshalledValue = underTest.unmarshal(xmlValue);

        // then
        assertThat(unmarshalledValue).isNull();
    }

    @Test
    public void shouldMarshalToIsoDateTime() throws Exception {
        // given
        final LocalDateTime javaValue = LocalDateTime.of(2019, Month.AUGUST, 31, 14, 29, 59);

        // when
        final String marshalledValue = underTest.marshal(javaValue);

        // then
        assertThat(marshalledValue).isEqualTo("2019-08-31T14:29:59");
    }

}

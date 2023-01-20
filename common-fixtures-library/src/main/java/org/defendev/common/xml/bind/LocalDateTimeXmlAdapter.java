package org.defendev.common.xml.bind;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;



public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String value) throws Exception {
        if (isNull(value) || value.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(value, FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime value) throws Exception {
        if (isNull(value)) {
            return null;
        }
        return value.format(FORMATTER);
    }

}

package org.defendev.common.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;



public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public ZonedDateTimeDeserializer() {
        this(null);
    }

    public ZonedDateTimeDeserializer(Class<ZonedDateTime> type) {
        super(type);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context)
        throws IOException, JacksonException {
        final String stringValue = parser.getText();
        return ZonedDateTime.parse(stringValue, formatter);
    }

}

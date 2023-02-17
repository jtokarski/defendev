package org.defendev.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;



public class ZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public ZonedDateTimeSerializer() {
        this(null);
    }

    public ZonedDateTimeSerializer(Class<ZonedDateTime> type) {
        super(type);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(formatter.format(value));
    }

}

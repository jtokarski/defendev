package org.defendev.common.jackson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class<LocalDateTime> type) {
        super(type);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(formatter.format(value));
    }

}

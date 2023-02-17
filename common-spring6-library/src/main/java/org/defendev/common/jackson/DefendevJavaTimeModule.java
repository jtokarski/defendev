package org.defendev.common.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


public class DefendevJavaTimeModule extends Module {

    @Override
    public String getModuleName() {
        return "defendevJavaTimeModule";
    }

    @Override
    public Version version() {
        return new Version(0, 0, 1, "", "", "");
    }

    @Override
    public void setupModule(SetupContext context) {
        final SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        serializers.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
        context.addSerializers(serializers);
    }
}

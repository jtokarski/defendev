package org.defendev.common.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import org.defendev.common.domain.iam.IDefendevUserDetails;



public class DefendevCommonDtoModule extends Module {

    @Override
    public String getModuleName() {
        return "defendevCommonDtoModule";
    }

    @Override
    public Version version() {
        return new Version(0, 0, 1, "", "", "");
    }

    @Override
    public void setupModule(SetupContext context) {
        final SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(IDefendevUserDetails.class, new DefendevUserDetailsDtoDeserializer());
        context.addDeserializers(deserializers);
    }

}

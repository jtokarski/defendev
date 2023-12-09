package org.defendev.common.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.defendev.common.domain.iam.DefendevUserDetailsDto;
import org.defendev.common.domain.iam.IDefendevUserDetails;

import java.io.IOException;



public class DefendevUserDetailsDtoDeserializer extends StdDeserializer<IDefendevUserDetails> {

    public DefendevUserDetailsDtoDeserializer() {
        this(null);
    }

    public DefendevUserDetailsDtoDeserializer(Class<IDefendevUserDetails> type) {
        super(type);
    }

    @Override
    public IDefendevUserDetails deserialize(JsonParser parser, DeserializationContext context)
        throws IOException, JacksonException {
        return parser.readValueAs(DefendevUserDetailsDto.class);
    }

}

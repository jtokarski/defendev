package org.defendev.common.fixture.loader.exception;

public class MissingHardcodedIdException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Missing hardcoded Id for entity: [%s]";

    public MissingHardcodedIdException(Class<?> entityClazz) {
        super(String.format(MESSAGE_TEMPLATE, entityClazz.getCanonicalName()));
    }

}

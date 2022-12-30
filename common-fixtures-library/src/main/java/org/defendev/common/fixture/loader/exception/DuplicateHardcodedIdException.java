package org.defendev.common.fixture.loader.exception;

public class DuplicateHardcodedIdException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE =
        "Duplicate hardcoded Id for entity: [%s] fixture Id: [%s]";

    public DuplicateHardcodedIdException(Class<?> entityClazz, Object hardcodedId) {
        super(String.format(MESSAGE_TEMPLATE, entityClazz.getCanonicalName(), hardcodedId));
    }

}

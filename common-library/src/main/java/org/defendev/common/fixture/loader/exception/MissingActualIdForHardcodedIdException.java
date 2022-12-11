package org.defendev.common.fixture.loader.exception;

public class MissingActualIdForHardcodedIdException extends RuntimeException {

    public MissingActualIdForHardcodedIdException(Class<?> entityClazz, Object hardcodedId) {
        super(String.format("Missing actualId for entity[%s] for hardcodedId[%s]", entityClazz.getCanonicalName(),
            hardcodedId.toString()));
    }

}

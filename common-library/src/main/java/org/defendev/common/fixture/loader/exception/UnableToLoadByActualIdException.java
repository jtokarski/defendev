package org.defendev.common.fixture.loader.exception;

public class UnableToLoadByActualIdException extends RuntimeException {

    public UnableToLoadByActualIdException(Class<?> entityClazz, Object actualId) {
        super(String.format("Unable to load entity[%s] for actualId[%s]", entityClazz.getCanonicalName(),
            actualId.toString()));
    }

}

package org.defendev.common.domain.exception;

import org.apache.commons.lang3.RandomStringUtils;



public interface IdentifiableException {

    String getExceptionId();

    static String generateExceptionId() {
        return RandomStringUtils.secure().next(8, "1234567890abcdefghijklmnopqrstuvwxyz");
    }

}

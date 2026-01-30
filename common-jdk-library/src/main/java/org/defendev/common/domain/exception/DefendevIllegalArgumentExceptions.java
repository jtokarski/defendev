package org.defendev.common.domain.exception;

import org.defendev.common.domain.error.ErrorDto;



public class DefendevIllegalArgumentExceptions {

    /**
     * Indicate situation when a user/client have made some action request on particular resource
     * but the resource id embedded in URI indicates different resource than the request
     * payload (e.g. command in JSON). To be thrown by @Controller.
     *
     * @return DefendevIllegalArgumentException with prefilled ErrorDto
     */
    public static DefendevIllegalArgumentException resourceIdMismatch() {
        return new DefendevIllegalArgumentException(new ErrorDto(
            "resource_id_mismatch",
            "Mismatch of resource id between URI and request payload",
            "", "", null
        ));
    }

}

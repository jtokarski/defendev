package org.defendev.common.domain.exception;

import org.defendev.common.domain.error.ErrorDto;

import static java.lang.String.format;



public class DefendevIllegalStateExceptions {

    /**
     * Indicate situation when no current request attributes present in execution context.
     * To be thrown when RequestContextHolder.currentRequestAttributes() fails.
     *
     * @return DefendevIllegalStateException with prefilled ErrorDto
     */
    public static DefendevIllegalStateException executionContextNoCurrentAttributes() {
        return new DefendevIllegalStateException(new ErrorDto(
            "execution_context_no_current_attributes",
            "No current request attributes present in execution context",
            "", "", null
        ));
    }

    /**
     * Indicate situation when some attribute is expected in the Execution Context (whether it's
     * reactor.util.context.Context or ThreadContext) but is not present. To be used by classes
     * that rely on something being present Execution Context and need to make fail-fast assertion
     * that it exists.
     *
     * @param attributeKey identifier by witch the attribute is retrieved
     * @param attributeDescription description of the attribute value
     * @param requiredBy name of the component or library which needs the value of the attribute
     *     like Spring Data Audit, Hibernate Envers, etc.
     *
     * @return DefendevIllegalStateException with prefilled ErrorDto
     */
    public static DefendevIllegalStateException executionContextAttributeMissing(String attributeKey,
                                                                                 String attributeDescription,
                                                                                 String requiredBy) {
        return new DefendevIllegalStateException(new ErrorDto(
            "execution_context_attribute_missing",
            "Attribute missing in execution context",
            format("attributeName:%s, attributeDescription:%s, requiredBy:%s", attributeKey, attributeDescription,
                requiredBy),
            "", null
        ));
    }

}

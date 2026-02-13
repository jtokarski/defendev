package org.defendev.common.spring6;

import org.defendev.common.domain.exception.DefendevIllegalStateExceptions;
import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static java.util.Objects.isNull;



/**
 * This utility is intended to handle two distinct usage scenarios:
 * 1. Spring Web Servlet stack. In this case Springs takes care of populating and clean-up of request attributes.
 * 2. Kafka Listener stack. In this case we have to assure that.
 * For this reason, both getters and setters use ExecutionContextHolder.currentRequestAttributes()
 * to assert that attribute container was initialized (i.e. ExecutionContextHolder is used properly).
 *
 * Methods of this class follow this concept:
 * - setters - per type of information
 * - private getters - per type of information
 * - public getters - per specific purpose of information (accepting the tradeoff of high specificity)
 *
 *
 */
public class ExecutionContextHolder {

    private static final String DEFENDEV_USER_DETAILS = "DEFENDEV_USER_DETAILS";

    private static final String DEFENDEV_AUDIT_CHECKPOINT_TYPE = "DEFENDEV_AUDIT_CHECKPOINT_TYPE";

    /**
     * Mainly for Kafka Listener scenario.
     */
    public static void reset() {
        RequestContextHolder.resetRequestAttributes();
    }

    public static void setUserDetails(IDefendevUserDetails userDetails) {
        currentRequestAttributes()
            .setAttribute(DEFENDEV_USER_DETAILS,  userDetails, RequestAttributes.SCOPE_REQUEST);
    }

    public static IDefendevUserDetails getUserDetails() {
        final IDefendevUserDetails userDetails = (IDefendevUserDetails) currentRequestAttributes()
            .getAttribute(DEFENDEV_USER_DETAILS, RequestAttributes.SCOPE_REQUEST);
        if (isNull(userDetails)) {
            throw DefendevIllegalStateExceptions.executionContextAttributeMissing(
                DEFENDEV_USER_DETAILS,
                "Details of logged-in user",
                "Spring Data Audit"
            );
        }
        return userDetails;
    }

    public static void setAuditCheckpointType(String auditCheckpointType) {
        currentRequestAttributes()
            .setAttribute(DEFENDEV_AUDIT_CHECKPOINT_TYPE, auditCheckpointType, RequestAttributes.SCOPE_REQUEST);
    }

    public static String getSpringDataAuditor() {
        return getUserDetails().getUsername();
    }

    public static String getHibernateEnversUsername() {
        return getUserDetails().getUsername();
    }

    public static String getEnversCheckpointType() {
        return (String) currentRequestAttributes()
            .getAttribute(DEFENDEV_AUDIT_CHECKPOINT_TYPE, RequestAttributes.SCOPE_REQUEST);
    }

    private static RequestAttributes currentRequestAttributes() {
        try {
            return RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            throw DefendevIllegalStateExceptions.executionContextNoCurrentAttributes();
        }
    }

}

package org.defendev.common.net.ssl;

import javax.net.ssl.HostnameVerifier;


/**
 * This class consists exclusively of static methods that return a javax.net.ssl.HostnameVerifier
 * with specific behavior.
 *
 *
 */
public class HostnameVerifierFactory {

    /**
     * Returns most permissive possible (always accepting) HostnameVerifier.
     *
     * @return implementation of javax.net.ssl.HostnameVerifier
     */
    public static HostnameVerifier lax() {
        return new LaxHostnameVerifier();
    }


}

package org.defendev.common.net.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Permissive, "accept-all" implementation of javax.net.ssl.HostnameVerifier
 *
 */
public class LaxHostnameVerifier implements HostnameVerifier {

    LaxHostnameVerifier() {}

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

}

package org.defendev.common.spring6.http.client;

import org.defendev.common.net.ssl.HostnameVerifierFactory;
import org.defendev.common.net.ssl.SSLContextFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.HttpURLConnection;



public class LaxSslRequestFactory extends SimpleClientHttpRequestFactory {

    private final SSLContext laxSslContext = SSLContextFactory.lax();

    private final SSLSocketFactory laxSslSocketFactory = laxSslContext.getSocketFactory();

    private final HostnameVerifier laxHostnameVerifier = HostnameVerifierFactory.lax();

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            final HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
            httpsConnection.setSSLSocketFactory(laxSslSocketFactory);
            httpsConnection.setHostnameVerifier(laxHostnameVerifier);
        }
        super.prepareConnection(connection, httpMethod);
    }

}

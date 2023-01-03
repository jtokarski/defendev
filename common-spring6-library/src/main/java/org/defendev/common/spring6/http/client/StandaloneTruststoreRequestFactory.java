package org.defendev.common.spring6.http.client;

import org.defendev.common.net.ssl.SSLContextFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.HttpURLConnection;



public class StandaloneTruststoreRequestFactory extends SimpleClientHttpRequestFactory {

    private final SSLContext sslContext;

    private final SSLSocketFactory sslSocketFactory;

    public StandaloneTruststoreRequestFactory(String storeFilePath, char[] storePassword, String storeType) {
        sslContext = SSLContextFactory.withTrustStoreFilePath(storeFilePath, storePassword, storeType);
        sslSocketFactory = sslContext.getSocketFactory();
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            final HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
            httpsConnection.setSSLSocketFactory(sslSocketFactory);
        }
        super.prepareConnection(connection, httpMethod);
    }

}

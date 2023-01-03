package org.defendev.common.net.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;



public class SSLContextFactory {

    public static SSLContext lax() {
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            final TrustManager trustManager = new LaxX509TrustManager();
            final TrustManager[] trustManagers = { trustManager };
            sslContext.init(null, trustManagers, null);
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IllegalStateException("An error occurred while creating SSLContext", e);
        }
    }

    public static SSLContext withTrustStoreFilePath(String storeFilePath, char[] storePassword, String storeType) {
        try (
            final InputStream storeInputStream = new FileInputStream(storeFilePath)
        ) {
            return withTrustStore(storeInputStream, storePassword, storeType);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to access the trust store file", e);
        }
    }

    public static SSLContext withTrustStore(InputStream storeInputStream, char[] storePassword, String storeType) {

        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");

            final KeyStore trustKeyStore = KeyStore.getInstance(storeType);
            trustKeyStore.load(storeInputStream, storePassword);

            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustKeyStore);
            final TrustManager[] trustManagers = tmf.getTrustManagers();

            /*
             *
             * If ever needed to init() the SSLContext also with keyManager, this can be done with something
             * like:
             *
             * final KeyStore keyStore = KeyStore.getInstance(storeType);
             * keyStore.load(storeInputStream, storePassword);
             *
             * final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
             * kmf.init(keyStore, storePassword);
             * final KeyManager[] keyManagers = kmf.getKeyManagers();
             *
             */

            sslContext.init(null, trustManagers, null);

            return sslContext;
        } catch (
            NoSuchAlgorithmException |
            KeyStoreException |
            IOException |
            CertificateException |
            KeyManagementException e
        ) {
            throw new IllegalStateException("An error occurred while creating SSLContext", e);
        }
    }

}

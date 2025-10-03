package org.defendev.common.net.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;



public class ProgrammaticKeytool {

    private static final DateTimeFormatter ALIAS_DATE_SEGMENT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static X509Certificate[] capture(String host, int port) {
        final SSLContext sslContext = SSLContextFactory.lax();
        final SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        try {
            final SSLSocket socket = (SSLSocket) socketFactory.createSocket(host, port);
            socket.startHandshake();
            final Certificate[] certificates = socket.getSession().getPeerCertificates();
            socket.close();
            return (X509Certificate[]) certificates;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void captureToFile(String host, int port, String outputFilePath) {
        final X509Certificate[] captured = capture(host, port);
        // The first certificate in the chain is the server's certificate
        final X509Certificate serverCertificate = captured[0];
        try {
            byte[] serverCertificateEncoded = serverCertificate.getEncoded();
            final Path path = Path.of(outputFilePath);
            if (Files.notExists(path.getParent())) {
                throw new IllegalArgumentException("Output files location does not exist");
            }
            Files.write(path, serverCertificateEncoded);
        } catch (CertificateEncodingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void captureAndAddToKeyStore(String host, int port,
                                               String inStorePath, char[] inStorePassword,
                                               String outStorePath, char[] outStorePassword,
                                               boolean serverCertificateOnly) {
        final X509Certificate[] capturedCertificates = capture(host, port);
        if (isNull(capturedCertificates) || capturedCertificates.length < 1) {
            throw new IllegalStateException("Unable to capture certificates");
        }
        try {
            final Path outPath = Path.of(outStorePath);
            if (Files.notExists(outPath.getParent())) {
                throw new IllegalArgumentException("Output files location does not exist");
            }

            final InputStream keyStoreInputStream;
            if (nonNull(inStorePath)) {
                final Path inPath = Path.of(inStorePath);
                if (Files.notExists(inPath)) {
                    throw new IllegalArgumentException("Input KeyStore does not exist: " + inPath);
                }
                keyStoreInputStream = Files.newInputStream(inPath);
            } else {
                keyStoreInputStream = null;
            }

            final KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(keyStoreInputStream, inStorePassword);

            final int limit = serverCertificateOnly ? 1 : capturedCertificates.length;
            int j = 0;
            final String dateSegment = ALIAS_DATE_SEGMENT.format(LocalDate.now(ZoneId.of("Z")));
            for (int i=0; i<limit; i+=1) {
                final X509Certificate capturedCertificate = capturedCertificates[i];
                String alias;
                do {
                    j += 1;
                    alias = "capture-" + dateSegment + "-" + j;
                } while (keyStore.containsAlias(alias));
                keyStore.setCertificateEntry(alias, capturedCertificate);
            }
            try (final OutputStream keyStoreOutputStream = Files.newOutputStream(outPath)) {
                keyStore.store(keyStoreOutputStream, outStorePassword);
            }
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }

}

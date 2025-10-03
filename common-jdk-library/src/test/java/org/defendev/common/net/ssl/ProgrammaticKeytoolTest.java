package org.defendev.common.net.ssl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;



public class ProgrammaticKeytoolTest {

    @Disabled("Depends on network connection.")
    @Test
    public void shouldCapture() {
        final X509Certificate[] captured = ProgrammaticKeytool.capture("self-signed.badssl.com", 443);
        assertThat(captured).isNotNull();
    }

    @Disabled("Depends on network connection and file paths.")
    @Test
    public void shouldCaptureToFile() {
        ProgrammaticKeytool.captureToFile("self-signed.badssl.com", 443, "/tmp/badssl.cer");
        /*
         * To verify correctness of the file:
         * $ keytool -importcert -file /tmp/badssl.cer -alias aaazzz888 -keystore /tmp/cacerts002
         *
         */
    }

    @Disabled("Depends on network connection and file paths.")
    @Test
    public void shouldCaptureAndAddToKeyStore() {
        ProgrammaticKeytool.captureAndAddToKeyStore("self-signed.badssl.com", 443,
            "/opt/jdk-21.0.4+7/lib/security/cacerts", "changeid".toCharArray(),
            "/tmp/cacerts002", "qwer0987".toCharArray(), true
        );
        /*
         * Resulting file can be verified with:
         * $ keytool -list -v -keystore /tmp/cacerts002
         *
         */
    }

}

package org.defendev.common.spring6.web.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;



public class RestTemplateFactoryTest {


    @DisplayName("laxSsl() should accept self-signed certificate")
    @Test
    public void laxSslSelfSigned() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);

        final HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        final RestTemplate underTest = RestTemplateFactory.laxSsl();
        final ResponseEntity<String> responseEntity = underTest.exchange(
            "https://self-signed.badssl.com/", HttpMethod.GET, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @DisplayName("laxSsl() should accept wrong-host certificate")
    @Test
    public void laxSslWrongHost() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);

        final HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        final RestTemplate underTest = RestTemplateFactory.laxSsl();
        final ResponseEntity<String> responseEntity = underTest.exchange(
            "https://wrong.host.badssl.com/", HttpMethod.GET, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @DisplayName("standaloneTruststore() should act based on provided trust store")
    @Test
    public void withTrustStoreTest(@TempDir final Path tempDir) throws IOException, InterruptedException {

        final Path serverCerPath = tempDir.resolve("self-signed-badssl.cer");
        final Path newKeystorePath = tempDir.resolve("my_new_keystore");

        final String keytoolPath = System.getProperty("java.home") + "/bin/keytool";
        final ProcessBuilder keytoolPrintcertBuilder = new ProcessBuilder(keytoolPath, "-printcert",
            "-sslserver", "self-signed.badssl.com", "-rfc")
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .redirectOutput(ProcessBuilder.Redirect.to(serverCerPath.toFile()));
        final Process keytoolPrintcertProcess = keytoolPrintcertBuilder.start();
        keytoolPrintcertProcess.waitFor();

        final ProcessBuilder keytoolImportcertBuilder = new ProcessBuilder(keytoolPath, "-importcert",
            "-file", serverCerPath.toString(),
            "-alias", "self-signed-badssl",
            "-keystore", newKeystorePath.toString(),
            "-storetype", "PKCS12",
            "-storepass", "abc1xyz3ASDF",
            "-noprompt")
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        final Process keytoolImportcert = keytoolImportcertBuilder.start();
        keytoolImportcert.waitFor();

        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
        final HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        final RestTemplate defaultRestTemplate = new RestTemplate();
        final RestTemplate underTest = RestTemplateFactory.standaloneTruststore(
            newKeystorePath.toString(), "abc1xyz3ASDF".toCharArray(), "PKCS12"
        );

        assertThatThrownBy(() -> {
            final ResponseEntity<String> responseEntity = defaultRestTemplate.exchange(
                "https://self-signed.badssl.com/", HttpMethod.GET, requestEntity, String.class
            );
        }).isInstanceOf(RestClientException.class).hasMessageContaining("PKIX");

        final ResponseEntity<String> responseEntity = underTest.exchange(
            "https://self-signed.badssl.com/", HttpMethod.GET, requestEntity, String.class
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

}

package org.defendev.common.spring6.web.client;

import org.defendev.common.spring6.http.client.LaxSslRequestFactory;
import org.defendev.common.spring6.http.client.StandaloneTruststoreRequestFactory;
import org.springframework.web.client.RestTemplate;



public class RestTemplateFactory {

    public static RestTemplate laxSsl() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new LaxSslRequestFactory());
        return restTemplate;
    }

    public static RestTemplate standaloneTruststore(String storeFilePath, char[] storePassword, String storeType) {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new StandaloneTruststoreRequestFactory(
            storeFilePath, storePassword, storeType
        ));
        return restTemplate;
    }

}

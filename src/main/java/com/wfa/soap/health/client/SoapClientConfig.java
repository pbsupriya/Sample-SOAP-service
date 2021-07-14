package com.wfa.soap.health.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j.support.CryptoFactoryBean;

import java.io.IOException;

@Configuration
public class SoapClientConfig {

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // set security actions
        securityInterceptor.setSecurementActions("Timestamp Signature Encrypt");

        // sign the request
        securityInterceptor.setSecurementUsername("ucanrwebad.ucanr.edu");
        securityInterceptor.setSecurementPassword("changeit");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

        // encrypt the request
        securityInterceptor.setSecurementEncryptionUser("ucanrwebad.ucanr.edu");
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementEncryptionParts("{Content}{http://memorynotfound.com/beer}getBeerRequest");

        return securityInterceptor;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("changeit");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("client-truststore.jks"));
        return cryptoFactoryBean;
    }

    @Bean
    public Jaxb2Marshaller getMarshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("edu.universityofcalifornia.ucpath");
        return marshaller;
    }

    @Bean
    public CustomClient getCustomClient() throws Exception {
        CustomClient beerClient = new CustomClient();
        beerClient.setMarshaller(getMarshaller());
        beerClient.setUnmarshaller(getMarshaller());
        beerClient.setDefaultUri("http://localhost:8080/UCPath/UpdatePersonJobRequest");
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        beerClient.setInterceptors(interceptors);
        return beerClient;
    }
}

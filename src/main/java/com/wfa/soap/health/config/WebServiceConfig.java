package com.wfa.soap.health.config;

import com.wfa.soap.health.endpoint.WebServiceEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j.support.CryptoFactoryBean;
import org.springframework.ws.soap.security.xwss.callback.KeyStoreCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import java.io.IOException;
import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/UCPath/*");
    }

    @Bean(name = "UCIDMServiceSOAP")
    public Wsdl11Definition UCIDMServiceSOAPDefinitions() {

        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/v1_05/UCIDMService_V1.05.wsdl"));

        return wsdl11Definition;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        try {
            interceptors.add(wss4jSecurityInterceptor());
            interceptors.add(new CustomEndpointInterceptor());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("could not initialize security interceptor");
        }
    }

    @Bean
    public KeyStoreCallbackHandler securityCallbackHandler() {
        KeyStoreCallbackHandler callbackHandler = new KeyStoreCallbackHandler();
        callbackHandler.setPrivateKeyPassword("changeit");
        return callbackHandler;
    }

    @Bean
    public Wss4jSecurityInterceptor wss4jSecurityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // validate incoming request
        //securityInterceptor.setValidationActions("Timestamp Signature Encrypt");
        securityInterceptor.setValidationSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationDecryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());

        // encrypt the response
        //securityInterceptor.setSecurementActions("NoSecurity");
        securityInterceptor.setSecurementEncryptionUser("ucanrwebad.ucanr.edu");
        securityInterceptor.setSecurementEncryptionParts("{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
        securityInterceptor.setSecurementEncryptionCrypto(getSignCryptoFactoryBean().getObject());

        // sign the response
        securityInterceptor.setSecurementActions("Signature Encrypt");
        securityInterceptor.setSecurementUsername("ucanrwebad.ucanr.edu");
        securityInterceptor.setSecurementPassword("changeit");
        securityInterceptor.setSecurementSignatureCrypto(getSignCryptoFactoryBean().getObject());

        securityInterceptor.setSecurementSignatureKeyIdentifier("DirectReference");
        return securityInterceptor;
    }

    @Bean
    public CryptoFactoryBean getSignCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("changeit");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("serverks"));
        return cryptoFactoryBean;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("changeit");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("msg-trust.jks"));
        return cryptoFactoryBean;
    }
}

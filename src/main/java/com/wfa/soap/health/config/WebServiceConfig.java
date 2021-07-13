package com.wfa.soap.health.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.KeyStoreCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);

        return new ServletRegistrationBean(servlet, "/UCPath/*");
    }

    @Bean(name = "UCIDMServiceSOAP")
    public Wsdl11Definition UCIDMServiceSOAPDefinitions() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/v1_05/UCIDMService_V1.05.wsdl"));
        return wsdl11Definition;
    }

    @Bean
    public XwsSecurityInterceptor securityInterceptor() {
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        securityInterceptor.setCallbackHandler(callbackHandler());
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return securityInterceptor;
    }

    @Bean
    public KeyStoreCallbackHandler callbackHandler() {
        try (InputStream fos = new FileInputStream("/Users/ravi/Downloads/mulesoft_integration/test/client-truststore.jks")) {
            KeyStore ks = KeyStore.getInstance("pkcs12");
            char[] pwdArray = "changeit".toCharArray();
            ks.load(fos, pwdArray);
            KeyStoreCallbackHandler handler = new KeyStoreCallbackHandler();
            handler.setTrustStore(ks);
            return handler;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new KeyStoreCallbackHandler();
    }
}

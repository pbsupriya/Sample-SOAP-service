package com.wfa.soap.health.client;

import edu.universityofcalifornia.ucpath.UCPathServiceResponseType;
import edu.universityofcalifornia.ucpath.idm.UpdatePersonJobRequestType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
        CustomClient wsclient = context.getBean(CustomClient.class);
        UpdatePersonJobRequestType request = new UpdatePersonJobRequestType();
        request.setCorrelationId("111222");
        UCPathServiceResponseType resp = wsclient.getBeer(request);
        System.out.println("response: " + resp);
    }
}

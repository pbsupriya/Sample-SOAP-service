package com.wfa.soap.health.client;

import edu.universityofcalifornia.ucpath.UCPathServiceResponseType;
import edu.universityofcalifornia.ucpath.idm.UpdatePersonJobRequestType;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CustomClient  extends WebServiceGatewaySupport {

    public UCPathServiceResponseType getBeer(UpdatePersonJobRequestType request){
        return (UCPathServiceResponseType) getWebServiceTemplate()
                .marshalSendAndReceive(request);

    }

}

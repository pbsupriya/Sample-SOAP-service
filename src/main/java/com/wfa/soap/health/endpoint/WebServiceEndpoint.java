package com.wfa.soap.health.endpoint;

import edu.universityofcalifornia.ucpath.ObjectFactory;
import edu.universityofcalifornia.ucpath.UCPathServiceResponseType;
import edu.universityofcalifornia.ucpath.idm.DeleteEmplIDRequestType;
import edu.universityofcalifornia.ucpath.idm.UpdateDepartmentRequestType;
import edu.universityofcalifornia.ucpath.idm.UpdateJobCodeRequestType;
import edu.universityofcalifornia.ucpath.idm.UpdatePersonJobRequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.StringWriter;

@Endpoint
public class WebServiceEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceEndpoint.class);

    public static final String NAMESPACE_URI = "http://www.universityofcalifornia.edu/UCPath/IDM";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdatePersonJobRequest")
    @SoapAction("http://www.universityofcalifornia.edu/UCPath/IDM/UpdatePersonJob")
    @ResponsePayload
    public JAXBElement<String> UpdatePersonJob(@RequestPayload UpdatePersonJobRequestType encryptedRequest) {

        try {

            logger.info("correlId={}", encryptedRequest.getCorrelationId());
            ObjectFactory factory = new ObjectFactory();
            //return factory.createUCPathServiceResponse(new UCPathServiceResponseType());
            QName qname = new QName("http://www.universityofcalifornia.edu/UCPath", "UCPathServiceResponse");
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            soapEnvelope.createName(NAMESPACE_URI);

            // Two ways to extract headers
            SOAPHeader soapHeader = soapEnvelope.getHeader();
            soapHeader = soapMessage.getSOAPHeader();

            // Two ways to extract body
            SOAPBody soapBody = soapEnvelope.getBody();
            soapBody = soapMessage.getSOAPBody();

            // To add some element
            SOAPFactory soapFactory = SOAPFactory.newInstance();


            logger.info("correlId={}", encryptedRequest.getCorrelationId());
            UCPathServiceResponseType responseType = new UCPathServiceResponseType();

            soapBody.addTextNode("test");

            responseType.setCorrelationId("123");
            responseType.setMsgCode(new JAXBElement<>(qname, String.class, "Testing"));
            responseType.setMsgDescDetails(new JAXBElement<>(qname, String.class, "Testing"));


            WebServiceEndpoint instance = new WebServiceEndpoint();

            JAXBContext context = JAXBContext.newInstance(responseType.getClass().getPackage().getName());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal(new JAXBElement<UCPathServiceResponseType>(new QName("uri", "local"), UCPathServiceResponseType.class, responseType), sw);
            String xml = sw.toString();

            return new JAXBElement<>(qname, String.class, "Testing");
            //return soapMessage;

            /*JAXBElement<UCPathServiceResponseType> elem =
                    new ObjectFactory().createUCPathServiceResponse(responseType);

            return new JAXBElement<>(qname, String.class, xml);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

        //return elem;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateJobCodeRequest")
    @ResponsePayload
    public JAXBElement<UpdateJobCodeRequestType> UpdateJobCode(@RequestPayload JAXBElement<UpdateJobCodeRequestType> encryptedRequest) {
        String outputString = "Successfully Request Validated for " + encryptedRequest + "!";
        return encryptedRequest;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateDepartmentRequest")
    @ResponsePayload
    public JAXBElement<UpdateDepartmentRequestType> UpdateDepartment(@RequestPayload JAXBElement<UpdateDepartmentRequestType> encryptedRequest) {
        String outputString = "Successfully Request Validated for " + encryptedRequest + "!";
        return encryptedRequest;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteEmplIDRequest")
    @ResponsePayload
    public JAXBElement<DeleteEmplIDRequestType> DeleteEmplID(@RequestPayload JAXBElement<DeleteEmplIDRequestType> encryptedRequest) {
        String outputString = "Successfully Request Validated for " + encryptedRequest + "!";
        return encryptedRequest;
    }
}

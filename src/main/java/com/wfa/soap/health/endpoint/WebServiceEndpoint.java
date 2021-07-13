package com.wfa.soap.health.endpoint;

import edu.universityofcalifornia.ucpath.UCPathServiceResponseType;
import edu.universityofcalifornia.ucpath.idm.DeleteEmplIDRequestType;
import edu.universityofcalifornia.ucpath.idm.UpdateDepartmentRequestType;
import edu.universityofcalifornia.ucpath.idm.UpdateJobCodeRequestType;
import edu.universityofcalifornia.ucpath.idm.UpdatePersonJobRequestType;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

@Endpoint
public class WebServiceEndpoint {

	private static final String NAMESPACE_URI = "http://www.universityofcalifornia.edu/UCPath/IDM";

	@PayloadRoot(namespace = "http://www.w3.org/2001/04/xmlenc#", localPart = "EncryptedData")
	@ResponsePayload
	public JAXBElement<UCPathServiceResponseType> EncryptedData(@RequestPayload JAXBElement<EncryptedData> encryptedData) throws JAXBException {

		String outputString = "Successfully Request Validated for !";
		UCPathServiceResponseType ucPathServiceResponseType = new UCPathServiceResponseType();

		UCPathServiceResponseType responseType = new UCPathServiceResponseType();
		responseType.setCorrelationId("123");

		QName qname = new QName("http://www.universityofcalifornia.edu/UCPath", "UCPathServiceResponse");
		return new JAXBElement(qname, UCPathServiceResponseType.class,responseType);
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdatePersonJobRequest")
	@ResponsePayload
	public JAXBElement<UCPathServiceResponseType> UpdatePersonJob(@RequestPayload UpdatePersonJobRequestType encryptedRequest) {
		String outputString = "Successfully Request Validated for " + encryptedRequest + "!";
		UCPathServiceResponseType ucPathServiceResponseType = new UCPathServiceResponseType();

		UCPathServiceResponseType responseType = new UCPathServiceResponseType();
		responseType.setCorrelationId("123");
		QName qname = new QName("http://www.universityofcalifornia.edu/UCPath", "UCPathServiceResponse");
		return new JAXBElement(qname, UCPathServiceResponseType.class,responseType);
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

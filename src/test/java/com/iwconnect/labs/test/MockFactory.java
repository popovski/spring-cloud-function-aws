package com.iwconnect.labs.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwconnect.labs.student.domain.Student;
import com.iwconnect.labs.student.dto.StudentPojo;

public class MockFactory {
	public static String mockFirstName = "User First name";
	public static String mockLastName = "User Last Name";
	
	public static Student studentMock() {
		Student mock = new Student();
		
		mock.setUuid(UUID.randomUUID().toString());
		mock.setCreatedDate(new Date());
		mock.setFirstName(mockFirstName);
		mock.setLastName(mockLastName);
		
		return mock;
	}
	
	public static StudentPojo studentPojoMock() {
		StudentPojo mock = new StudentPojo();
		
		mock.setFirstName(mockFirstName);
		mock.setLastName(mockLastName);
		
		return mock;
	}
	
	public static APIGatewayProxyRequestEvent apiGatewayProxyRequestEventMock(ObjectMapper mapper) throws JsonProcessingException {
		APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
		ProxyRequestContext requestContext = new ProxyRequestContext();
		Map<String, String> headers = new HashMap<>();
		
		event.setBody(mapper.writeValueAsString(MockFactory.studentPojoMock()));
		
		requestContext.setStage("local");
		headers.put("Host", "localhost");
		event.setHeaders(headers);
		event.setRequestContext(requestContext);
		
		return event;
	}
}
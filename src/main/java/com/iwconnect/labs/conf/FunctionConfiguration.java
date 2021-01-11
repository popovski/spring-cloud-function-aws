package com.iwconnect.labs.conf;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwconnect.labs.student.domain.Student;
import com.iwconnect.labs.student.dto.StudentPojo;
import com.iwconnect.labs.student.service.StudentService;
import com.iwconnect.labs.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class FunctionConfiguration {
	private static Logger logger = LoggerFactory.getLogger(FunctionConfiguration.class);

	@Autowired
	StudentService studentService;
	
	@Bean
	public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createStudent() {
		logger.info("Execute Lambda createStudent");

		return value -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				StudentPojo studentPojo = Utils.studentMapper(value, mapper);
				Student student = studentService.saveStudent(studentPojo);

				return createResponseEvent(student);
			} catch (Exception e) {
				logger.error("Error executing createStudent function", e);
				e.printStackTrace();
				return new APIGatewayProxyResponseEvent().withStatusCode(500);
			}
		};
	}

	private APIGatewayProxyResponseEvent createResponseEvent(Student student) {
		logger.info("Execute createResponseEvent method");
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
		ObjectMapper mapper = new ObjectMapper();
		try {
			responseEvent.setStatusCode(201);
			responseEvent.setHeaders(createResultHeader());
			responseEvent.setBody(mapper.writeValueAsString(student));
		} catch (Exception e) {
			logger.error("Error executing createResponseEvent method", e);
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
		return responseEvent;
	}

	private Map<String, String> createResultHeader() {
		logger.info("Execute createResultHeader method");
		Map<String, String> resultHeader = new HashMap<>();
		resultHeader.put("Content-Type", "application/json");

		return resultHeader;
	}
}
package com.iwconnect.labs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwconnect.labs.conf.FunctionConfiguration;
import com.iwconnect.labs.student.dto.StudentPojo;

public class Utils {
	private static Logger logger = LoggerFactory.getLogger(FunctionConfiguration.class);
	
	public static StudentPojo studentMapper(APIGatewayProxyRequestEvent value, ObjectMapper mapper) {
		logger.info("Executing studentMapper method");
		StudentPojo studentPojo = null;
		try {
			studentPojo = mapper.readValue(value.getBody(), StudentPojo.class);
			logger.info("Successfully deserialized student '{}'", studentPojo);
		} catch (Exception e) {
			logger.error("Error executing studentMapper method", e);
		}

		return studentPojo;
	}
}

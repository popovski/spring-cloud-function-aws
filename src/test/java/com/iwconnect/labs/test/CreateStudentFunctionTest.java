package com.iwconnect.labs.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwconnect.labs.student.domain.Student;
import com.iwconnect.labs.student.dto.StudentPojo;
import com.iwconnect.labs.student.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateStudentFunctionTest {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

    @MockBean
    private StudentService studentService;
    
	@Test
	public void createStudentTest() throws Exception {

		try {
			String requestPayload = mapper.writeValueAsString(MockFactory.apiGatewayProxyRequestEventMock(mapper));
			Student student = MockFactory.studentMock();
			
			when(this.studentService.saveStudent(any(StudentPojo.class))).thenReturn(student);
			
			this.mockMvc.perform(post("/createStudent").content(requestPayload))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
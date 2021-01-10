package com.iwconnect.labs.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.iwconnect.labs.student.domain.Student;
import com.iwconnect.labs.student.dto.StudentPojo;
import com.iwconnect.labs.student.repository.StudentRepository;
import com.iwconnect.labs.student.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	StudentRepository studentRepository;

	@Override
	public Student saveStudent(StudentPojo studentPojo) throws Exception {
		logger.info("Saving student {} into DB", studentPojo);
		Student transientStudent = new Student();
		try {
			// mapping attributes
			transientStudent.setFirstName(studentPojo.getFirstName());
			transientStudent.setLastName(studentPojo.getLastName());
			// save to DB
			return studentRepository.save(transientStudent);
		} catch (Exception e) {
			logger.error("Error saving student ", e);
			throw e;
		}
	}
}
package com.iwconnect.labs.student.service;

import com.iwconnect.labs.student.domain.Student;
import com.iwconnect.labs.student.dto.StudentPojo;

public interface StudentService {
	public Student saveStudent(StudentPojo responseData) throws Exception;
}

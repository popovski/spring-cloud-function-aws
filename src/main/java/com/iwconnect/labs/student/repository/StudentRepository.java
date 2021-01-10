package com.iwconnect.labs.student.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.iwconnect.labs.student.domain.Student;

@EnableScan
public interface StudentRepository extends CrudRepository<Student, String> {

}

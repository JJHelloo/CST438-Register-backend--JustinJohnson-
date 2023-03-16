package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

// Student findByEmail(String email);

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/student/new")
	public Student createStudent(String name, String email) {
		Student s = new Student(); // calling from student.java
		
		s.setName(name);
		// check if email is used, throws an Exception if so
		if(studentRepository.findByEmail(email) != null) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
		else 
			s.setEmail(email);
		
		studentRepository.save(s);
		return s;
	}
	
	@PostMapping("/student/new")
	public Student studentHold(String email) {
		Student s = studentRepository.findByEmail(email);
		
		if(s != null) {
			if(s.getStatusCode() == 0) {
				s.setStatus("Hold");
				s.setStatusCode(1);
				studentRepository.findByEmail(email);
				studentRepository.save(s);
			} else 
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, s.getName() + " already has a hold");
		} else 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, email + " doesn't exist");
		
		return s;
	}
	public Student studentHoldRelease(String email) {
		Student s = studentRepository.findByEmail(email);
		
		if(s != null) {
			if(s.getStatusCode() == 1) {
				s.setStatus(null);
				s.setStatusCode(1);
				studentRepository.findByEmail(email);
				studentRepository.save(s);
			} else 
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, s.getName() + " doesn't have a hold");
		} else 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, email + " doesn't exist");
		
		return s;
	}

} // end of student controller class

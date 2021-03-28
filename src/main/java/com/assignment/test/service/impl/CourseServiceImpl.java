package com.assignment.test.service.impl;

import com.assignment.test.model.entity.Course;
import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;
import com.assignment.test.repository.CourseRepository;
import com.assignment.test.repository.UserRepository;
import com.assignment.test.service.CourseService;
import com.assignment.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepository;

	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public void addCourses() {
		List<Course> courses = new ArrayList<>();
		Course course1 = new Course();
		course1.setCourseName("A1 - J2EE Design Patterns");
		courses.add(course1);
		Course course2 = new Course();
		course2.setCourseName("A2 - Enterprise Service Bus");
		courses.add(course2);
		Course course3 = new Course();
		course3.setCourseName("A3 - Service Oriented Architecture");
		courses.add(course3);
		Course course4 = new Course();
		course4.setCourseName("A4 - Web Service");
		courses.add(course4);
		Course course5 = new Course();
		course5.setCourseName("A5 - Web Services Security");
		courses.add(course5);
		Course course6 = new Course();
		course6.setCourseName("A6 - Secure Messaging");
		courses.add(course6);

		courseRepository.saveAll(courses);

	}

	@Override
	public Course findCourseByCourseName(String courseName) {
		return courseRepository.findByCourseName(courseName);
	}
}

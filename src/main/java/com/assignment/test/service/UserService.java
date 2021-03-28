package com.assignment.test.service;

import javax.servlet.http.HttpServletRequest;

import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;

public interface UserService {
	double calculateCourseCost(String status);

	double processTotal(String[] classes, double courseCost, boolean hotel, boolean permit);

	double getHOTEL_FEE();

	double getPARKING_FEE();

	User insertUser(User user);

	User getUserId(String email);

	void insertRegistrationCourses(int courseId, Integer registrationId);

	UserRegistrationInfo getConfirmedRegistrationInfo(String email);

	String performRemovedUserSelection(HttpServletRequest request);

	String performCartUserService(HttpServletRequest request);

	String performConfirmUserService(HttpServletRequest request);

	void sendEmail(User user,UserRegistrationInfo userRegistrationInfo);
}

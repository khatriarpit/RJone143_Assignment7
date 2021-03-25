package com.assignment.test.service;

import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;

public interface UserService {
    double calculateCourseCost(String status);
    double processTotal(String [] classes, double courseCost, boolean hotel, boolean permit);
    double getHOTEL_FEE();
    double getPARKING_FEE();

    void insertUser(User user);

    User getUserId(String email);

    void insertRegistrationCourses(int courseId, Integer registrationId);

    UserRegistrationInfo getConfirmedRegistrationInfo(String email);
}

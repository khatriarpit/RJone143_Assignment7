package com.assignment.test.service;

import com.assignment.test.model.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    void addCourses();

    Course findCourseByCourseName(String courseName);
}

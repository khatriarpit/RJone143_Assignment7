package com.assignment.test.repository;

import com.assignment.test.model.entity.Course;
import com.assignment.test.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseName(String courseName);
}

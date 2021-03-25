package com.assignment.test.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Course")
public class Course {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String courseName;


	@ManyToMany(targetEntity = Registration.class,cascade = CascadeType.ALL )
	List<Registration> registrations;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}
}

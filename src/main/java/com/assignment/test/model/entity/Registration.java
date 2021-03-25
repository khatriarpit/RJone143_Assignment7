package com.assignment.test.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Registration")
public class Registration {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private boolean hotel;
    private boolean permit;
    private double courseCost;
	private double totalCost;

	@ManyToMany(targetEntity = Course.class, mappedBy = "registrations", cascade = CascadeType.ALL)
	private List<Course> courses;

	private Integer user;


    public boolean isHotel() {
		return hotel;
	}
	public void setHotel(boolean hotel) {
		this.hotel = hotel;
	}
	public boolean isPermit() {
		return permit;
	}
	public void setPermit(boolean permit) {
		this.permit = permit;
	}
	public double getCourseCost() {
		return courseCost;
	}
	public void setCourseCost(double courseCost) {
		this.courseCost = courseCost;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}

package com.assignment.test.model.entity;

import java.io.Serializable;

public class UserRegistrationInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
    private String[] courses;
    private String status;
    private boolean hotel;
    private boolean permit;
    private double courseCost;
	private double totalCost;
	
    // Getters and Setters
 
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getCourses() {
		return courses;
	}

	public void setCourses(String[] courses) {
		this.courses = courses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

    public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getCourseCost() {
		return courseCost;
	}

	public void setCourseCost(double courseCost) {
		this.courseCost = courseCost;
	}
}

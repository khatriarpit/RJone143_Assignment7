package com.assignment.test.service.impl;

import com.assignment.test.repository.UserRepository;
import com.assignment.test.model.entity.Course;
import com.assignment.test.model.entity.Registration;
import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;
import com.assignment.test.service.CourseService;
import com.assignment.test.service.RegistrationService;
import com.assignment.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	CourseService courseService;

	@Autowired
	RegistrationService registrationService;

	@Autowired
	private JavaMailSender javaMailSender;

	private final double EMPLOYEE_COST = 850;
	private final double STUDENT_COST = 1000;
	private final double SPEAKER_COST = 0;
	private final double OTHERS_COST = 1350;
	private final double HOTEL_FEE = 185;
	private final double PARKING_FEE = 10;

	@Autowired
	private UserRepository userRepository;

	// calculate the total for the seminar attendance
	public double calculateCourseCost(String status) {

		double courseCost;

		switch (status) {
		case "JHU Employee":
			courseCost = EMPLOYEE_COST;
			break;
		case "JHU Student":
			courseCost = STUDENT_COST;
			break;
		case "Speaker":
			courseCost = SPEAKER_COST;
			break;
		case "Guest":
			courseCost = OTHERS_COST;
			break;
		default:
			courseCost = OTHERS_COST;
		}

		return courseCost;

	}

	public double processTotal(String[] classes, double courseCost, boolean hotel, boolean permit) {
		double totalCost;

		Integer numClasses = classes.length;
		double classCost = courseCost * numClasses;

		// Explicitly code all possible fee scenarios
		if (hotel && !permit) {
			totalCost = classCost + HOTEL_FEE;
		} else if (hotel && permit) {
			totalCost = classCost + HOTEL_FEE;
		} else if (!hotel && permit) {
			totalCost = classCost + PARKING_FEE;
		} else {
			totalCost = classCost;
		}

		return totalCost;
	}

	public double getHOTEL_FEE() {
		return HOTEL_FEE;
	}

	public double getPARKING_FEE() {
		return PARKING_FEE;
	}

	@Override
	public User insertUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserId(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public void insertRegistrationCourses(int courseId, Integer registrationId) {

		String query = "INSERT INTO course_registrations (courses_id, registrations_id) " + "VALUES (?, ?)";

		em.createNativeQuery(query).setParameter(1, courseId).setParameter(2, registrationId).executeUpdate();
	}

	@Override
	@Transactional
	public UserRegistrationInfo getConfirmedRegistrationInfo(String email) {
		String query = "SELECT r.hotel, r.permit, r.course_cost, r.total_cost, u.name, u.email, u.status FROM registration as r JOIN user as u ON r.user = u.id WHERE email = ?";
		List<Object[]> list = em.createNativeQuery(query).setParameter(1, email).getResultList();
		UserRegistrationInfo userRegistrationInfo = new UserRegistrationInfo();

		for (Object[] obj : list) {
			userRegistrationInfo.setHotel(Boolean.valueOf(obj[0].toString()));
			userRegistrationInfo.setPermit(Boolean.valueOf(obj[1].toString()));
			userRegistrationInfo.setCourseCost(Double.valueOf(obj[2].toString()));
			userRegistrationInfo.setTotalCost(Double.valueOf(obj[3].toString()));
			userRegistrationInfo.setName(String.valueOf(obj[4].toString()));
			userRegistrationInfo.setEmail(String.valueOf(obj[5].toString()));
			userRegistrationInfo.setStatus(String.valueOf(obj[6].toString()));
		}
		return userRegistrationInfo;
	}

	@Override
	public String performRemovedUserSelection(HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session.getAttribute("userRegistrationInfo");

		String course = request.getParameter("course");

		String message;
		if (course == null || course.isEmpty()) {
			message = "Please provide a course to remove.";

			request.setAttribute("message", message);
		} else {
			String[] courses = userRegistrationInfo.getCourses();

			// Logic to get array of classes and remove the course we no longer wish to take
			List<String> list = new ArrayList<String>(Arrays.asList(courses));
			list.remove(course);
			courses = list.toArray(new String[0]);
			userRegistrationInfo.setCourses(courses);
			userRegistrationInfo.setTotalCost(processTotal(courses, userRegistrationInfo.getCourseCost(),
					userRegistrationInfo.isHotel(), userRegistrationInfo.isPermit()));

			// update session after processing again
			session.setAttribute("userRegistrationInfo", userRegistrationInfo);
		}
		return "/show_registration_info";
	}

	@Override
	public String performCartUserService(HttpServletRequest request) {
		// Get the entered registration data
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String[] courses = request.getParameterValues("courses");
		String status = request.getParameter("status");
		String hotel = request.getParameter("hotel");
		String permit = request.getParameter("permit");

		// validate the parameters
		String message;
		if (name == null || email == null || status == null || name.isEmpty() || email.isEmpty() || status.isEmpty()
				|| courses.length < 1) {
			message = "Please fill out at least your name, email, desired classes, and employment status.";
			request.setAttribute("message", message);
			return "/index";
		}
		// we have valid params, we can use form to update registrationInfo and apply it
		// to the session
		else {
			// Determine if we have already have session otherwise create it
			HttpSession session = request.getSession();
			UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session
					.getAttribute("userRegistrationInfo");
			if (userRegistrationInfo == null) {
				userRegistrationInfo = new UserRegistrationInfo();
			}
			userRegistrationInfo.setName(name);
			userRegistrationInfo.setEmail(email);
			userRegistrationInfo.setCourses(courses);
			userRegistrationInfo.setStatus(status);
			userRegistrationInfo.setHotel(Boolean.parseBoolean(hotel));
			userRegistrationInfo.setPermit(Boolean.parseBoolean(permit));
			userRegistrationInfo.setCourseCost(calculateCourseCost(status));
			userRegistrationInfo.setTotalCost(processTotal(courses, userRegistrationInfo.getCourseCost(),
					Boolean.parseBoolean(hotel), Boolean.parseBoolean(permit)));
			message = "";
			session.setAttribute("userRegistrationInfo", userRegistrationInfo);
			session.setAttribute("hotelFee", getHOTEL_FEE());
			session.setAttribute("parkingFee", getPARKING_FEE());
			return "/show_registration_info";
		}
	}

	@Override
	public String performConfirmUserService(HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session.getAttribute("userRegistrationInfo");
		request.setAttribute("userRegistrationInfo", userRegistrationInfo);
		session.removeAttribute("userRegistrationInfo");

		User user = new User();
		user.setName(userRegistrationInfo.getName());
		user.setEmail(userRegistrationInfo.getEmail());
		user.setStatus(userRegistrationInfo.getStatus());

		User userObj=insertUser(user);
//		User userObj = getUserId(user.getEmail());
		Integer userId = userObj.getId();

		Registration registration = new Registration();
		registration.setHotel(userRegistrationInfo.isHotel());
		registration.setPermit(userRegistrationInfo.isPermit());
		registration.setCourseCost(userRegistrationInfo.getCourseCost());
		registration.setTotalCost(userRegistrationInfo.getTotalCost());
		registration.setUser(userId);

		String[] courses = userRegistrationInfo.getCourses();

		// Get Course
		List<Course> courseList = courseService.getAllCourses();
		if (courseList.isEmpty() && courseList.size() <= 0) {
			courseService.addCourses();
		}

		List<Course> courseObectList = new ArrayList<>();
		for (Integer i = 0; i < courses.length; i++) {
			Course courseObect1 = courseService.findCourseByCourseName(courses[i]);
			courseObectList.add(courseObect1);
		}
		registration.setCourses(courseObectList);
		registrationService.insertRegistration(registration);
		UserRegistrationInfo confirmedUserRegistrationInfo = getConfirmedRegistrationInfo(
				userRegistrationInfo.getEmail());
		request.setAttribute("userRegistrationInfo", confirmedUserRegistrationInfo);
		sendEmail(user,userRegistrationInfo);
		return "confirm";
	}

	@Override
	public void sendEmail(User user, UserRegistrationInfo userRegistrationInfo) {

		System.out.println("Sending Email to "+user.getEmail());		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());
		msg.setSubject("Thanks for Register with us.");
		msg.setText(user.getName());
		msg.setText(" You selected to be registered as a <b>" + userRegistrationInfo.getStatus() + "</b>.");

		javaMailSender.send(msg);
		
		System.out.println("Email Sent Successfully.");		
	}
}

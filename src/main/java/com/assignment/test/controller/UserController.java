package com.assignment.test.controller;

import com.assignment.test.model.entity.Course;
import com.assignment.test.model.entity.Registration;
import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;
import com.assignment.test.service.CourseService;
import com.assignment.test.service.RegistrationService;
import com.assignment.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	CourseService courseService;


	@PostMapping("/registration")
	public String registration(Model model, @RequestParam(name = "action") String action, HttpServletRequest request) {
		String url = "index.jsp";

		if (action == null) {
			action = "cart"; // default action
		}
		if (action.equals("remove")) {
			return userService.performRemovedUserSelection(request);
		} else if (action.equals("cart")) {
			return userService.performCartUserService(request);
		} else if (action.equals("confirm")) {
			return userService.performConfirmUserService(request);
			
		}
		return "registration";
	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Model model) {
		return "index";
	}
}

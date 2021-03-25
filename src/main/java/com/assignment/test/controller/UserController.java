package com.assignment.test.controller;

import com.assignment.test.model.entity.Registration;
import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;
import com.assignment.test.service.RegistrationService;
import com.assignment.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {
    @Autowired
    private UserService worker;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/registration")
    public String registration(Model model, @RequestParam(name = "action") String action, HttpServletRequest request) {
        String url = "index.jsp";

        if (action == null) {
            action = "cart";  // default action
        }
        if (action.equals("remove")) {
            url = "/show_registration_info.jsp";    // the "index" page
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
                userRegistrationInfo.setTotalCost(worker.processTotal(courses, userRegistrationInfo.getCourseCost(), userRegistrationInfo.isHotel(), userRegistrationInfo.isPermit()));

                // update session after processing again
                session.setAttribute("userRegistrationInfo", userRegistrationInfo);
            }
        }
        // This is the default action from index.jsp
        else if (action.equals("cart")) {
            // Get the entered registration data
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String[] courses = request.getParameterValues("courses");
            String status = request.getParameter("status");
            String hotel = request.getParameter("hotel");
            String permit = request.getParameter("permit");


            // validate the parameters
            String message;
            if (name == null || email == null || status == null ||
                    name.isEmpty() || email.isEmpty() || status.isEmpty()
                    || courses.length < 1) {
                message = "Please fill out at least your name, email, desired classes, and employment status.";
                url = "/index.jsp";

                request.setAttribute("message", message);
            }
            // we have valid params, we can use form to update registrationInfo and apply it to the session
            else {
                // Determine if we have already have session otherwise create it
                HttpSession session = request.getSession();
                UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session.getAttribute("userRegistrationInfo");
                if (userRegistrationInfo == null) {
                    userRegistrationInfo = new UserRegistrationInfo();
                }

//                UserService worker = new UserService();

                userRegistrationInfo.setName(name);
                userRegistrationInfo.setEmail(email);
                userRegistrationInfo.setCourses(courses);
                userRegistrationInfo.setStatus(status);
                userRegistrationInfo.setHotel(Boolean.parseBoolean(hotel));
                userRegistrationInfo.setPermit(Boolean.parseBoolean(permit));

                userRegistrationInfo.setCourseCost(worker.calculateCourseCost(status));
                userRegistrationInfo.setTotalCost(worker.processTotal(courses, userRegistrationInfo.getCourseCost(), Boolean.parseBoolean(hotel), Boolean.parseBoolean(permit)));

                message = "";
                url = "show_registration_info";

                // we can use these attributes across pages with the session
                session.setAttribute("userRegistrationInfo", userRegistrationInfo);
                session.setAttribute("hotelFee", worker.getHOTEL_FEE());
                session.setAttribute("parkingFee", worker.getPARKING_FEE());
                return url;
            }
        } else if (action.equals("confirm")) {
            // session gets removed, we send params as a request to confirm instead for now so session gets cleared but we can still see data
            HttpSession session = request.getSession();
            UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) session.getAttribute("userRegistrationInfo");
            request.setAttribute("userRegistrationInfo", userRegistrationInfo);
            session.removeAttribute("userRegistrationInfo");
            url = "/confirm.jsp";

            User user = new User();
            user.setName(userRegistrationInfo.getName());
            user.setEmail(userRegistrationInfo.getEmail());
            user.setStatus(userRegistrationInfo.getStatus());

            worker.insertUser(user);
            User userObj = worker.getUserId(user.getEmail());
            Integer userId = userObj.getId();

            Registration registration = new Registration();
            registration.setHotel(userRegistrationInfo.isHotel());
            registration.setPermit(userRegistrationInfo.isPermit());
            registration.setCourseCost(userRegistrationInfo.getCourseCost());
            registration.setTotalCost(userRegistrationInfo.getTotalCost());
            registration.setUser(userId);

            registrationService.insertRegistration(registration);
            Registration registrationObject = registrationService.getRegistrationId(userId);
            Integer registrationId = registrationObject.getId();
            String[] courses = userRegistrationInfo.getCourses();

            for (Integer i = 0; i < courses.length; i++) {
                Matcher matcher = Pattern.compile("\\d+").matcher(courses[i]);
                matcher.find();
                int courseId = Integer.valueOf(matcher.group());
                worker.insertRegistrationCourses(courseId, registrationId);
            }
            UserRegistrationInfo confirmedUserRegistrationInfo = worker.getConfirmedRegistrationInfo(userRegistrationInfo.getEmail());
            request.setAttribute("userRegistrationInfo", confirmedUserRegistrationInfo);

            return "confirm";
        }
        return "registration";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "index";
    }
}

package com.assignment.test.service.impl;

import com.assignment.test.repository.UserRepository;
import com.assignment.test.model.entity.User;
import com.assignment.test.model.entity.UserRegistrationInfo;
import com.assignment.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;

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
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserId(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void insertRegistrationCourses(int courseId, Integer registrationId) {

        String query
                = "INSERT INTO course_registrations (courses_id, registrations_id) "
                + "VALUES (?, ?)";

        em.createNativeQuery(query)
                .setParameter(1, courseId)
                .setParameter(2, registrationId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public UserRegistrationInfo getConfirmedRegistrationInfo(String email) {
        String query = "SELECT r.hotel, r.permit, r.course_cost, r.total_cost, u.name, u.email, u.status FROM Registration as r JOIN User as u ON r.user = u.id WHERE email = ?";
       List<Object[]> list= em.createNativeQuery(query).
                setParameter(1, email)
                .getResultList();
        UserRegistrationInfo userRegistrationInfo=    new UserRegistrationInfo();

        for(Object[] obj:list){
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
}

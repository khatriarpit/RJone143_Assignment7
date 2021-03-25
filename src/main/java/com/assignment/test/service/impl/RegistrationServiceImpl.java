package com.assignment.test.service.impl;

import com.assignment.test.model.entity.Registration;
import com.assignment.test.repository.RegistrationRepository;
import com.assignment.test.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;

    @Override
    public void insertRegistration(Registration registration) {
        registrationRepository.save(registration);
    }

    @Override
    public Registration getRegistrationId(Integer userId) {
        return registrationRepository.findByUser(userId);
    }
}

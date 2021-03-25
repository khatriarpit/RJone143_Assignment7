package com.assignment.test.service;

import com.assignment.test.model.entity.Registration;

public interface RegistrationService {
    void insertRegistration(Registration registration);

    Registration getRegistrationId(Integer userId);
}

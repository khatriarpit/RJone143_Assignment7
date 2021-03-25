package com.assignment.test.repository;

import com.assignment.test.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Registration findByUser(Integer userId);
}

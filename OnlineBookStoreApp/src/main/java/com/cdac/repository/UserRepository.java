package com.cdac.repository;

import com.cdac.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByMobileNumber(String mobileNumber);

    boolean existsByEmail(String email);
}

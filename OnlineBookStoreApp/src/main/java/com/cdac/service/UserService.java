package com.cdac.service;

import com.cdac.entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    User getUserByEmail(String email);
}

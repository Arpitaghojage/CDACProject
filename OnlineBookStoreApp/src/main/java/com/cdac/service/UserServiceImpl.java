package com.cdac.service;

import com.cdac.entities.User;

import java.util.List;

public class UserServiceImpl  implements  UserService {

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}

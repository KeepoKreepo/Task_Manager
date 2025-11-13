package com.example.taskmanager.service;

import com.example.taskmanager.dao.UserDao;
import com.example.taskmanager.model.User;

import java.sql.SQLException;

public class AuthService {
    private final UserDao userDao = new UserDao();

    public void register(String name, String email, String password) throws SQLException {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userDao.insert(user);
    }
}
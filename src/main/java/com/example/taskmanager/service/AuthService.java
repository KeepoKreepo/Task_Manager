package com.example.taskmanager.service;

import com.example.taskmanager.dao.UserDao;
import com.example.taskmanager.model.User;

import java.sql.SQLException;
import java.util.List;

public class AuthService {

    private final UserDao userDao = new UserDao();

    public User login(String email, String password) throws SQLException {
        User user = userDao.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void register(String name, String email, String password) throws SQLException {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userDao.insert(user);
    }

    public static List<User> getAllUsers() throws SQLException {
        return UserDao.findAll();
    }

    public User getUserById(long id) throws SQLException {
        return userDao.findById(id);
    }
}
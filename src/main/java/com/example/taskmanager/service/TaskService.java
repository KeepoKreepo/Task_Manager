package com.example.taskmanager.service;

import com.example.taskmanager.dao.TaskDao;
import com.example.taskmanager.model.Task;

import java.sql.SQLException;


public class TaskService {
    private final TaskDao taskDao = new TaskDao();

    public boolean addTask(Task task) throws SQLException {
        return taskDao.insert(task);
    }
}
package com.example.taskmanager.service;

import com.example.taskmanager.dao.TaskDao;
import com.example.taskmanager.model.Task;

import java.sql.SQLException;
import java.util.List;


public class TaskService {

    private final TaskDao taskDao = new TaskDao();

    public boolean addTask(Task task) throws SQLException {
        return taskDao.insert(task);
    }

    public boolean updateStatus(long id, String status) throws SQLException {
        return taskDao.updateStatus(id, status);
    }

    public boolean updateDetails(long id, String title, String description) throws SQLException {
        return taskDao.updateDetails(id, title, description);
    }

    public boolean updateAssignment(long id, Long assignedTo) throws SQLException {
        return taskDao.updateAssignment(id, assignedTo);
    }

    public boolean updatePriority(long id, String priority) throws SQLException {
        return taskDao.updatePriority(id, priority);
    }

    public boolean updateProject(long id, String project) throws SQLException {
        return taskDao.updateProject(id, project);
    }

    public boolean delete(long id) throws SQLException {
        return taskDao.delete(id);
    }

    public List<Task> getTasksFromUser(long userId) throws SQLException {
        return taskDao.findByUser(userId);
    }
}
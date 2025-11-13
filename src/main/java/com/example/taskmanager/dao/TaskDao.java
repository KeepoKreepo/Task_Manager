package com.example.taskmanager.dao;

import com.example.taskmanager.config.DB;
import com.example.taskmanager.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class TaskDao {

    public boolean insert(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (title, description, status, user_id, assigned_to) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setLong(4, task.getUserId());

            if (task.getAssignedTo() != null)
                stmt.setLong(5, task.getAssignedTo());
            else
                stmt.setNull(5, Types.BIGINT);

            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateStatus(long id, String status) throws SQLException {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateDetails(long id, String title, String description) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, description = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

            public boolean delete(long id) throws SQLException {
                String sql = "DELETE FROM tasks WHERE id = ?";
                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setLong(1, id);
                    return stmt.executeUpdate() > 0;
                }
            }
        }
    }
}

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
}

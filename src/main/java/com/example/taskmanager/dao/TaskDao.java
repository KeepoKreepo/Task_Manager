package com.example.taskmanager.dao;

import com.example.taskmanager.config.DB;
import com.example.taskmanager.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

    public boolean insert(Task task) throws SQLException {
        String sql = """
            INSERT INTO tasks (title, description, status, user_id, assigned_to, priority, project)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

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

            stmt.setString(6, task.getPriority());
            stmt.setString(7, task.getProject());

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
        }
    }
    public boolean updateAssignment(long id, Long assignedTo) throws SQLException {
        String sql = "UPDATE tasks SET assigned_to = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (assignedTo != null)
                stmt.setLong(1, assignedTo);
            else
                stmt.setNull(1, Types.BIGINT);

            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updatePriority(long id, String priority) throws SQLException {
        String sql = "UPDATE tasks SET priority = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, priority);
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateProject(long id, String project) throws SQLException {
        String sql = "UPDATE tasks SET project = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project);
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Task> findByUser(long userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();

        String sql = """
            SELECT t.*,
                   u1.name AS creator_name,
                   u2.name AS assigned_to_name
            FROM tasks t
            JOIN users u1 ON t.user_id = u1.id
            LEFT JOIN users u2 ON t.assigned_to = u2.id
            WHERE t.user_id = ? OR t.assigned_to = ?
            ORDER BY t.id DESC
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setId(rs.getLong("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setStatus(rs.getString("status"));
                t.setUserId(rs.getLong("user_id"));
                t.setAssignedTo(rs.getObject("assigned_to") != null ? rs.getLong("assigned_to") : null);
                t.setCreatorName(rs.getString("creator_name"));
                t.setAssignedToName(rs.getString("assigned_to_name"));

                // NEW
                t.setPriority(rs.getString("priority"));
                t.setProject(rs.getString("project"));

                tasks.add(t);
            }
        }
        return tasks;
    }
}

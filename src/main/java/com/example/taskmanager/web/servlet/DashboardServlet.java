package com.example.taskmanager.web.servlet;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.AuthService;
import com.example.taskmanager.service.TaskService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long userId = (long) session.getAttribute("userId");
        String filterProject = req.getParameter("project");  // optional filter

        try {
            // Load all tasks for this user
            List<Task> tasks = taskService.getTasksFromUser(userId);

            // Load users (for assignment dropdown)
            List<User> users = authService.getAllUsers();
            req.setAttribute("users", users);

            // Compute unique project list
            Set<String> projectSet = tasks.stream()
                    .map(Task::getProject)
                    .filter(Objects::nonNull)
                    .filter(p -> !p.isBlank())
                    .collect(Collectors.toCollection(TreeSet::new));

            req.setAttribute("projects", projectSet);

            // Apply project filter
            if (filterProject != null && !filterProject.isBlank()) {
                tasks = tasks.stream()
                        .filter(t -> filterProject.equals(t.getProject()))
                        .collect(Collectors.toList());
            }

            req.setAttribute("tasks", tasks);

            req.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error loading dashboard data", e);
        }
    }
}

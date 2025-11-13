package com.example.taskmanager.web.servlet;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "/tasks/new",
        "/tasks/update-status",
        "/tasks/update-details",
        "/tasks/delete",

})
public class TaskServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        if ("/tasks/new".equals(path)) {
            try {
                AuthService authService = new AuthService();
                req.setAttribute("users", authService.getAllUsers());
                req.getRequestDispatcher("/WEB-INF/jsp/new-task.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading form");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getServletPath();

        try {
            switch (path) {
                case "/tasks/new" -> createTask(req, resp);
                case "/tasks/update-status" -> updateStatus(req, resp);
                case "/tasks/update-details" -> updateDetails(req, resp);
                case "/tasks/delete" -> deleteTask(req, resp);
                default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    private void createTask(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");

        Task task = new Task();
        task.setTitle(req.getParameter("title"));
        task.setDescription(req.getParameter("description"));
        task.setStatus("OPEN");
        task.setUserId(userId);

        String assignedStr = req.getParameter("assignedTo");
        if (assignedStr != null && !assignedStr.isEmpty()) {
            task.setAssignedTo(Long.parseLong(assignedStr));
        }

        taskService.addTask(task);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
    private void updateStatus(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        long id = Long.parseLong(req.getParameter("taskId"));
        String status = req.getParameter("status");
        taskService.updateStatus(id, status);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void updateDetails(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        long id = Long.parseLong(req.getParameter("taskId"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        taskService.updateDetails(id, title, description);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        long id = Long.parseLong(req.getParameter("taskId"));
        taskService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}

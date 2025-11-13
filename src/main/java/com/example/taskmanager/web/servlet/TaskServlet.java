package com.example.taskmanager.web.servlet;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "/tasks/new",
        "/tasks/update-status",
        "/tasks/update-details",
        "/tasks/delete",
        "/tasks/assign",
        "/tasks/update-priority",
        "/tasks/update-project"
})
public class TaskServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if ("/tasks/new".equals(req.getServletPath())) {
            try {
                // Load users for assignment dropdown
                com.example.taskmanager.service.AuthService auth = new com.example.taskmanager.service.AuthService();
                req.setAttribute("users", auth.getAllUsers());

                req.getRequestDispatcher("/WEB-INF/jsp/new-task.jsp").forward(req, resp);
            } catch (Exception e) {
                resp.sendError(500, "Error loading form");
            }
        } else {
            resp.sendError(405);
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
                case "/tasks/assign" -> updateAssignment(req, resp);
                case "/tasks/update-priority" -> updatePriority(req, resp);
                case "/tasks/update-project" -> updateProject(req, resp);
                default -> resp.sendError(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Error: " + e.getMessage());
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

        // Assignment
        String assignedStr = req.getParameter("assignedTo");
        task.setAssignedTo((assignedStr == null || assignedStr.isBlank()) ? null : Long.parseLong(assignedStr));

        // NEW priority + project
        task.setPriority(req.getParameter("priority"));
        task.setProject(req.getParameter("project"));

        taskService.addTask(task);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void updateStatus(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        taskService.updateStatus(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("status")
        );
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void updateDetails(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        taskService.updateDetails(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("title"),
                req.getParameter("description")
        );
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        taskService.delete(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void updateAssignment(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String assignedStr = req.getParameter("assignedTo");
        Long assigned = (assignedStr == null || assignedStr.isBlank())
                ? null
                : Long.parseLong(assignedStr);

        taskService.updateAssignment(Long.parseLong(req.getParameter("id")), assigned);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void updatePriority(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        taskService.updatePriority(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("priority")
        );
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        taskService.updateProject(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("project")
        );
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}

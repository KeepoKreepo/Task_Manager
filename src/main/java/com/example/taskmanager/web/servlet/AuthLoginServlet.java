package com.example.taskmanager.web.servlet;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class AuthLoginServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = authService.login(email, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getName());
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            } else {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Login failed", e);
        }
    }
}
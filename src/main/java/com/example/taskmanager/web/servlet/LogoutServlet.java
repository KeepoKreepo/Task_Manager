package com.example.taskmanager.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false); // don't create if not exists
        if (session != null) {
            session.invalidate(); // clear session
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
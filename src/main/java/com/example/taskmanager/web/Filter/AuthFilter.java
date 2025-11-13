package com.example.taskmanager.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        boolean isAuth = path.endsWith("login") || path.endsWith("register") || path.contains("css") || path.contains("js");

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("userId") != null;

        if (loggedIn || isAuth) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
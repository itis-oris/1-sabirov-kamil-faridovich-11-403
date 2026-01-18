package ru.itis.dis403.auction.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.service.UserService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("error", request.getParameter("error"));
        request.getRequestDispatcher("/login.ftlh").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(login(req));
    }

    private String login(HttpServletRequest req) throws UnsupportedEncodingException {
        HttpSession session = req.getSession(false);
        String resource = req.getContextPath() + "/";
        String error;

        try {
            if (session == null || session.getAttribute("user") == null) {
                String email = req.getParameter("email");
                String password = req.getParameter("password");

                User user = userService.authenticate(email, password);

                if (user != null) {
                    session = req.getSession(true);
                    session.setAttribute("user", user);
                    resource = req.getContextPath() + "/";
                } else {
                    error = URLEncoder.encode("Неверный email или пароль", "UTF-8");
                    resource = req.getContextPath() + "/login?error=" + error;
                }
            }
        } catch (Exception e) {
            error = URLEncoder.encode("Ошибка авторизации", "UTF-8");
            resource = req.getContextPath() + "/login?error=" + error;
        }
        return resource;
    }
}
package ru.itis.dis403.auction.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.service.UserService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", request.getParameter("error"));
        request.getRequestDispatcher("/WEB-INF/templates/pages/register.ftlh").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(register(req));
    }

    private String register(HttpServletRequest req) throws UnsupportedEncodingException {
        HttpSession session = req.getSession(false);
        String resource = "/";
        String error;

        try {
            if (session == null || session.getAttribute("user") == null) {
                UserService userService = (UserService) getServletContext().getAttribute("userService");

                String username = req.getParameter("username");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String passwordConfirm = req.getParameter("passwordConfirm");

                User user = userService.registerUser(username, email, password, passwordConfirm);

                session = req.getSession(true);
                session.setAttribute("user", user);
                resource = "/";

            } else {
                resource = "/";
            }
        } catch (IllegalArgumentException | SecurityException e) {
            error = URLEncoder.encode(e.getMessage(), "UTF-8");
            resource = "/register?error=" + error;
        } catch (Exception e) {
            error = URLEncoder.encode("Ошибка регистрации", "UTF-8");
            resource = "/register?error=" + error;
        }
        return resource;
    }
}
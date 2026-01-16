package ru.itis.dis403.auction.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.service.LicensePlateService;

import java.io.IOException;
import java.util.List;


@WebServlet(value = {"", "/home"})
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            req.setAttribute("user", session.getAttribute("user"));
        }
        try {
            LicensePlateService plateService = (LicensePlateService) getServletContext().getAttribute("plateService");
            List<LicensePlate> plates = plateService.getAllActivePlates();
            req.setAttribute("plates", plates);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Ошибка загрузки лотов: " + e.getMessage());
        }
        req.getRequestDispatcher("/home.ftlh").forward(req,resp);
    }

}
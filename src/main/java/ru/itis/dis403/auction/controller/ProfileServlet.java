package ru.itis.dis403.auction.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.service.BidService;
import ru.itis.dis403.auction.service.LicensePlateService;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        BidService bidService = (BidService) getServletContext().getAttribute("bidService");
        LicensePlateService plateService = (LicensePlateService) getServletContext().getAttribute("plateService");

        req.setAttribute("userBids", bidService.getUserBids(user.getId()));
        req.setAttribute("userLots", plateService.getUserLots(user.getId()));

        req.getRequestDispatcher("/profile.ftlh").forward(req, resp);
    }
}
package ru.itis.dis403.auction.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.dis403.auction.model.Bid;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.service.BidService;
import ru.itis.dis403.auction.service.LicensePlateService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/plate/*")
public class PlateServlet extends HttpServlet {

    private LicensePlateService plateService;
    private BidService bidService;

    @Override
    public void init() throws ServletException {
        super.init();
        plateService = (LicensePlateService) getServletContext().getAttribute("plateService");
        bidService = (BidService) getServletContext().getAttribute("bidService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("contextPath", request.getContextPath());
        showPlatePage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleBid(request, response);
    }

    private void showPlatePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            int plateId = Integer.parseInt(pathInfo.substring(1));

            LicensePlate plate = plateService.getPlateById(plateId);
            List<Bid> bids = bidService.getBidHistoryForPlate(plateId);

            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("plate", plate);
            request.setAttribute("bids", bids);

            request.getRequestDispatcher("/plate.ftlh").forward(request, response);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/?error=Лот не найден");
        }
    }

    private void handleBid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try {
            if (session == null || session.getAttribute("user") == null) {
                request.setAttribute("error", "Для ставки необходимо авторизоваться");
                showPlatePage(request, response);
                return;
            }

            User user = (User) session.getAttribute("user");
            int plateId = Integer.parseInt(request.getParameter("plateId"));
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));

            bidService.placeBid(plateId, amount, user);

            request.setAttribute("success", "Ставка успешно размещена!");
            showPlatePage(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            showPlatePage(request, response);
        }
    }
}
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/bid")
public class BidServlet extends HttpServlet {

    private BidService bidService;

    @Override
    public void init() throws ServletException {
        super.init();
        bidService = (BidService) getServletContext().getAttribute("bidService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String contextPath = req.getContextPath();

        try {
            if (session == null || session.getAttribute("user") == null) {
                resp.sendRedirect(contextPath + "/login?error=" +
                        URLEncoder.encode("Для ставки необходимо авторизоваться", "UTF-8"));
                return;
            }

            User user = (User) session.getAttribute("user");
            int plateId = Integer.parseInt(req.getParameter("plateId"));
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));

            // Размещаем ставку
            bidService.placeBid(plateId, amount, user);

            // Сохраняем сообщение в сессии
            session.setAttribute("successMessage", "Ставка успешно размещена!");

            // Redirect на страницу лота через GET
            resp.sendRedirect(contextPath + "/plate/" + plateId);

        } catch (NumberFormatException e) {
            handleErrorRedirect(req, resp, "Неверный формат данных");
        } catch (IllegalArgumentException | IllegalStateException e) {
            handleErrorRedirect(req, resp, e.getMessage());
        } catch (Exception e) {
            handleErrorRedirect(req, resp, "Ошибка при размещении ставки");
        }
    }

    private void handleErrorRedirect(HttpServletRequest req, HttpServletResponse resp, String message)
            throws IOException {
        HttpSession session = req.getSession();
        String contextPath = req.getContextPath();

        try {
            int plateId = Integer.parseInt(req.getParameter("plateId"));
            session.setAttribute("errorMessage", message);
            resp.sendRedirect(contextPath + "/plate/" + plateId);
        } catch (Exception ex) {
            resp.sendRedirect(contextPath + "/?error=" + URLEncoder.encode(message, "UTF-8"));
        }
    }
}
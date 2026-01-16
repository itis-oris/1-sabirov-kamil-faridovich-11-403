package ru.itis.dis403.auction.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.service.LicensePlateService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@WebServlet("/create-lot")
public class CreateLotServlet extends HttpServlet {

    private LicensePlateService plateService;

    @Override
    public void init() throws ServletException {
        super.init();
        plateService = (LicensePlateService) getServletContext().getAttribute("plateService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", request.getParameter("error"));
        request.getRequestDispatcher("/create-lot.ftlh").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(createLot(req));
    }

    private String createLot(HttpServletRequest req) throws UnsupportedEncodingException {
        HttpSession session = req.getSession(false);
        String resource = "/";
        String error;

        try {

            User user = (User) session.getAttribute("user");

            String number = req.getParameter("number");
            String region = req.getParameter("region");
            String description = req.getParameter("description");
            BigDecimal startingPrice = new BigDecimal(req.getParameter("startingPrice"));

            LicensePlate plate = new LicensePlate();
            plate.setNumber(number);
            plate.setRegion(region);
            plate.setDescription(description);
            plate.setStartingPrice(startingPrice);
            plate.setCurrentPrice(startingPrice);
            plate.setEndDate(LocalDateTime.now().plusDays(7));
            plate.setUserCreatorId(user.getId());

            plateService.createPlate(plate);

            resource = "/Auction/?success=" +
                    URLEncoder.encode("Лот успешно создан!", "UTF-8");

        } catch (NumberFormatException e) {
            error = URLEncoder.encode("Неверный формат цены", "UTF-8");
            resource = "/Auction/create-lot?error=" + error;
        } catch (IllegalArgumentException e) {
            error = URLEncoder.encode(e.getMessage(), "UTF-8");
            resource = "/Auction/create-lot?error=" + error;
        } catch (Exception e) {
            error = URLEncoder.encode("Ошибка при создании лота", "UTF-8");
            resource = "/Auction/create-lot?error=" + error;
        }

        return resource;
    }
}
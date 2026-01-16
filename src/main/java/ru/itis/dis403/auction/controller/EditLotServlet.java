package ru.itis.dis403.auction.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.service.LicensePlateService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@WebServlet("/edit-lot")
public class EditLotServlet extends HttpServlet {

    private LicensePlateService plateService;

    @Override
    public void init() throws ServletException {
        super.init();
        plateService = (LicensePlateService) getServletContext().getAttribute("plateService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/Auction/login?error=Для редактирования необходимо авторизоваться");
            return;
        }

        try {
            User user = (User) session.getAttribute("user");
            int plateId = Integer.parseInt(request.getParameter("id"));

            LicensePlate plate = plateService.getPlateById(plateId);

            if (plate == null || !plate.getUserCreatorId().equals(user.getId())) {
                response.sendRedirect("/Auction/profile?error=Лот не найден или у вас нет прав для редактирования");
                return;
            }

            request.setAttribute("plate", plate);
            request.setAttribute("error", request.getParameter("error"));
            request.getRequestDispatcher("/edit-lot.ftlh").forward(request, response);

        } catch (Exception e) {
            response.sendRedirect("/Auction/profile?error=Ошибка при загрузке лота");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(updateLot(request));
    }

    private String updateLot(HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = request.getSession(false);
        String resource = "/Auction/profile";
        String error;

        try {

            User user = (User) session.getAttribute("user");
            int plateId = Integer.parseInt(request.getParameter("plateId"));

            LicensePlate plate = plateService.getPlateById(plateId);
            if (plate == null || !plate.getUserCreatorId().equals(user.getId())) {
                error = URLEncoder.encode("Нет прав для редактирования этого лота", "UTF-8");
                return resource + "?error=" + error;
            }

            String number = request.getParameter("number");
            String region = request.getParameter("region");
            String description = request.getParameter("description");

            plate.setNumber(number);
            plate.setRegion(region);
            plate.setDescription(description);

            plateService.updatePlate(plate);

            session.setAttribute("successMessage", "Лот успешно обновлен");
            resource = "/Auction/profile";

        } catch (IllegalArgumentException e) {
            error = URLEncoder.encode(e.getMessage(), "UTF-8");
            int plateId = Integer.parseInt(request.getParameter("plateId"));
            return "/Auction/edit-lot?id=" + plateId + "&error=" + error;
        } catch (Exception e) {
            error = URLEncoder.encode("Ошибка при обновлении лота", "UTF-8");
            int plateId = Integer.parseInt(request.getParameter("plateId"));
            return "/Auction/edit-lot?id=" + plateId + "&error=" + error;
        }

        return resource;
    }
}
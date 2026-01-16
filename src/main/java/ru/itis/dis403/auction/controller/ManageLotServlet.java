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

@WebServlet("/manage-lot")
public class ManageLotServlet extends HttpServlet {

    private LicensePlateService plateService;

    @Override
    public void init() throws ServletException {
        super.init();
        plateService = (LicensePlateService) getServletContext().getAttribute("plateService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(manageLot(req));
    }

    private String manageLot(HttpServletRequest req) throws UnsupportedEncodingException {
        HttpSession session = req.getSession(false);
        String resource = "/Auction/profile";
        String error;

        try {
            User user = (User) session.getAttribute("user");
            String action = req.getParameter("action");
            int plateId = Integer.parseInt(req.getParameter("plateId"));

            LicensePlate plate = plateService.getPlateById(plateId);
            if (plate == null || !plate.getUserCreatorId().equals(user.getId())) {
                error = URLEncoder.encode("Лот не найден", "UTF-8");
                return resource + "?error=" + error;
            }
            switch (action) {
                case "delete":
                    plateService.deletePlate(plate);
                    session.setAttribute("successMessage", "Лот успешно удален");
                    break;

                case "update":
                    String number = req.getParameter("number");
                    String region = req.getParameter("region");
                    String description = req.getParameter("description");

                    plate.setNumber(number);
                    plate.setRegion(region);
                    plate.setDescription(description);

                    plateService.updatePlate(plate);
                    session.setAttribute("successMessage", "Лот успешно обновлен");
                    break;

                case "extend":
                    plate.setEndDate(plate.getEndDate().plusDays(3));
                    plateService.updatePlate(plate);
                    session.setAttribute("successMessage", "Аукцион продлен на 3 дня");
                    break;

                default:
                    error = URLEncoder.encode("Неизвестное действие", "UTF-8");
                    return resource + "?error=" + error;
            }

        } catch (NumberFormatException e) {
            error = URLEncoder.encode("Неверный формат данных", "UTF-8");
            return resource + "?error=" + error;
        } catch (IllegalArgumentException e) {
            error = URLEncoder.encode(e.getMessage(), "UTF-8");
            return resource + "?error=" + error;
        } catch (Exception e) {
            error = URLEncoder.encode("Ошибка при выполнении операции", "UTF-8");
            return resource + "?error=" + error;
        }

        return resource;
    }
}
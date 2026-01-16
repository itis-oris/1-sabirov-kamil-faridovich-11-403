package ru.itis.dis403.auction.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.auction.model.CompletedAuction;
import ru.itis.dis403.auction.service.BidService;

import java.io.IOException;
import java.util.List;

@WebServlet("/completed")
public class CompletedPlatesServlet extends HttpServlet {
    private BidService bidService;

    @Override
    public void init() throws ServletException {
        bidService = (BidService) getServletContext().getAttribute("bidService");
        System.out.println("CompletedPlatesServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<CompletedAuction> completedAuctions = bidService.getCompletedAuctions();
            request.setAttribute("completedAuctions", completedAuctions);
            request.setAttribute("title", "Завершенные аукционы");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ошибка при загрузке завершенных лотов");
        }

        request.getRequestDispatcher("/completed.ftlh").forward(request, response);
    }
}
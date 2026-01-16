package ru.itis.dis403.auction.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.dis403.auction.dao.BidDao;
import ru.itis.dis403.auction.dao.LicensePlateDao;
import ru.itis.dis403.auction.dao.UserDao;
import ru.itis.dis403.auction.service.BidService;
import ru.itis.dis403.auction.service.LicensePlateService;
import ru.itis.dis403.auction.service.UserService;
import ru.itis.dis403.auction.util.DatabaseConnection;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            Class.forName("org.postgresql.Driver");
            DatabaseConnection.getConnection();

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found");
            throw new RuntimeException("PostgreSQL driver not found", e);
        }

        UserDao userDao = new UserDao();
        LicensePlateDao plateDao = new LicensePlateDao();
        BidDao bidDao = new BidDao();

        UserService userService = new UserService(userDao);
        LicensePlateService plateService = new LicensePlateService(plateDao);
        BidService bidService = new BidService(bidDao, plateDao);

        context.setAttribute("userService", userService);
        context.setAttribute("plateService", plateService);
        context.setAttribute("bidService", bidService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnection.releaseConnection();
    }
}
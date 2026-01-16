package ru.itis.dis403.auction.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/static/") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.equals("/") ||
                path.equals("/home") ||
                path.equals("/logout") ||
                path.equals("/login") ||
                path.equals("/register") ||
                path.startsWith("/plate/") ||
                path.equals("/completed")) {

            chain.doFilter(request, response);
            return;
        }

        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}
package com.epam.rd.java.basic.repairagency.web.filter;

import com.epam.rd.java.basic.repairagency.util.web.SecurityUtil;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private static final Logger log = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String urlPattern = WebUtil.getUrlPattern(httpRequest);
        log.info("SecurityFilter: " + urlPattern);
        HttpSession session = httpRequest.getSession();
        boolean isLoggedIn = (session != null && WebUtil.getLoggedUser(httpRequest) != null);
        String appName = WebUtil.getAppName(httpRequest);
        String loginURI = appName + "/login";
        String registrationURI = appName + "/registration";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI) ||
                httpRequest.getRequestURI().equals(registrationURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp") ||
                httpRequest.getRequestURI().endsWith("registration.jsp");
//        log.info("isLoggedIn = " + isLoggedIn);
//        log.info("isLoginRequest = " + isLoginRequest);
//        log.info("isLoginPage = " + isLoginPage);
//        log.info("SecurityUtil.isLoggedUserHasPermission(httpRequest) = " + SecurityUtil.isLoggedUserHasPermission(httpRequest));
//        log.info("SecurityUtil.isLoginRequired(httpRequest) = " + SecurityUtil.isLoginRequired(httpRequest));
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the user is already logged in, and he's trying to log in again
            // then forward to the homepage
            String role = WebUtil.getLoggedUser(httpRequest).getRole().toString().toLowerCase();
            String address = WebUtil.getAppName(request) + "/" + role + "/home";
            ((HttpServletResponse) response).sendRedirect(address);
        } else if (isLoggedIn && !SecurityUtil.isLoggedUserHasPermission(httpRequest)) {
            // the user is already logged in, and he's trying to request page requires
            // permission, then forward to the error page
            String errorMessage = "Access denied";
            String address = "/pages/common/errors/error.jsp";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher =
                    request.getServletContext().getRequestDispatcher(address);
            dispatcher.forward(request, response);
        } else if (!isLoggedIn && SecurityUtil.isLoginRequired(httpRequest)) {
            // the user is not logged in, and the requested page requires
            // authentication, then forward to the login page
            String loginPage = appName + "/login";
            ((HttpServletResponse) response).sendRedirect(loginPage);
        } else {
            // for other requested pages that do not require authentication
            // or the user is already logged in, continue to the destination
            chain.doFilter(request, response);
        }
    }
}

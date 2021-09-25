package com.epam.rd.java.basic.repairagency.util.web;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.factory.ServiceFactory;
import com.epam.rd.java.basic.repairagency.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class WebUtil {

    private static final Logger log = LogManager.getLogger(WebUtil.class);

    public static User getLoggedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedUser");
    }

    public static String getAppName(ServletRequest request) {
        return request.getServletContext().getInitParameter("appName");
    }

    public static void logRequestParams(ServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> params = new ArrayList<>();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            params.add(name + " ==> " + request.getParameter(name));
        }
        log.info("Request params: " + params);
    }

    public static String getUrlPatternWithParametersExceptMessages(String referer) {
        StringBuilder address = new StringBuilder();
        String refererUrlPattern = WebUtil.getUrlPattern(referer);
        address.append(refererUrlPattern);
        List<String> parameters = WebUtil.getParameters(referer);
        parameters.removeIf(parameter -> parameter.contains("errorMessage") || parameter.contains("successMessage"));
        if (parameters.size() > 0) {
            address.append("?");
            for (String parameter : parameters) {
                address.append(parameter).append("&");
            }
        }
        return address.toString();
    }

    public static GenericService<? extends AbstractEntity> getService(HttpServletRequest request, Class<? extends GenericService<? extends AbstractEntity>> serviceClass) {
        ServiceFactory serviceFactory = (ServiceFactory) request.getServletContext().getAttribute(ServiceFactory.getName());
        return serviceFactory.getService(serviceClass);
    }

    public static String getUrlPattern(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    public static void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String target) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(target);
        requestDispatcher.forward(request, response);
    }

    public static String getUrlPattern(String url) {
        int endIndexOfUrlPattern = url.indexOf("?");
        if (endIndexOfUrlPattern != -1) {
            return url.substring(0, endIndexOfUrlPattern);
        } else {
            return url;
        }
    }

    public static List<String> getParameters(String url) {
        List<String> parameters = new ArrayList<>();
        int endIndexOfUrlPattern = url.indexOf("?");
        if (endIndexOfUrlPattern == -1) {
            return Collections.emptyList();
        }
        String urlParameters = url.substring(endIndexOfUrlPattern + 1);
        for (String parameter : urlParameters.split("&")) {
            String[] parameterNameAndValue = parameter.split("=");
            String name = parameterNameAndValue[0];
            String value = parameterNameAndValue[1];
            parameters.add(name + "=" + value);
        }
        return parameters;
    }

    public static User parseUserFromRequest(HttpServletRequest request) {
        User user = new User();
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            user.setId(Long.parseLong(id));
        }
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setPassword(request.getParameter("password"));
        return user;
    }

}

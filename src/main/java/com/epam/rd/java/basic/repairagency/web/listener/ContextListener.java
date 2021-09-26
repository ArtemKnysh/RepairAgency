package com.epam.rd.java.basic.repairagency.web.listener;

import com.epam.rd.java.basic.repairagency.factory.CommandFactory;
import com.epam.rd.java.basic.repairagency.factory.ServiceFactory;
import com.epam.rd.java.basic.repairagency.util.FactoryUtil;
import com.epam.rd.java.basic.repairagency.web.config.SecurityConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        configSecurity(sce);
        configCommandFactory(sce);
        configServiceFactory(sce);
//        SecurityConfig.printAll();
    }

    private void configSecurity(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String appName = servletContext.getInitParameter("appName");
        SecurityConfig.clearUrls();
        Enumeration<String> parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            if (!name.toLowerCase().contains("url")) {
                continue;
            }
            String parameter = servletContext.getInitParameter(name);
            parameter = parameter.substring(appName.length());
            SecurityConfig.putUrl(name, parameter);
        }
    }

    private void configCommandFactory(ServletContextEvent sce) {
        try {
            ServletContext servletContext = sce.getServletContext();
            String commandsImplPackageName = servletContext.getInitParameter("commandsImplPackageName");
            CommandFactory commandFactory = FactoryUtil.buildCommandFactory(commandsImplPackageName);
            sce.getServletContext().setAttribute(CommandFactory.getName(), commandFactory);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("CommandFactory configuration error", e);
        }
    }

    private void configServiceFactory(ServletContextEvent sce) {
        try {
            ServletContext servletContext = sce.getServletContext();
            String rootPackageName = servletContext.getInitParameter("rootPackageName");

            ServiceFactory serviceFactory = FactoryUtil.buildServiceFactory(rootPackageName);
            sce.getServletContext().setAttribute(ServiceFactory.getName(), serviceFactory);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("ServiceFactory configuration error", e);
        }
    }


}

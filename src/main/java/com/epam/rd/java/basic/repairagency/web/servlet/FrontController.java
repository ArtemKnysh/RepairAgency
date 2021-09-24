package com.epam.rd.java.basic.repairagency.web.servlet;

import com.epam.rd.java.basic.repairagency.factory.CommandFactory;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import com.epam.rd.java.basic.repairagency.web.command.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontController extends HttpServlet {

    protected CommandFactory commandFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        commandFactory = (CommandFactory) getServletContext().getAttribute(CommandFactory.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processInCommand(request, response, Method.GET, WebUtil.getUrlPattern(request));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processInCommand(request, response, Method.POST, WebUtil.getUrlPattern(request));
    }

    protected void processInCommand(HttpServletRequest request, HttpServletResponse response, Method method, String urlPattern)
            throws ServletException, IOException {
        Command command = commandFactory.getCommand(urlPattern, method);
        if (command != null) {
            command.process(request, response);
        } else {
            WebUtil.forwardToJsp(request, response, "/pages/common/errors/error-404.jsp");
        }
    }

}

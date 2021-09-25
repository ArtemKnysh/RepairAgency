package com.epam.rd.java.basic.repairagency.web.command.impl.base;

import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class GetCommand implements Command {

    protected abstract Logger getLogger();

    protected abstract String getErrorMessage(HttpServletRequest request);

    protected abstract void processRequest(HttpServletRequest request) throws Exception;

    protected String getSuccessAddress(HttpServletRequest request) {
        return "/pages/" + WebUtil.getUrlPattern(request) + ".jsp";
    }

    @Override
    public final void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getLogger().info("{}: {}", request.getMethod(), WebUtil.getUrlPattern(request));
        request.setAttribute("errorMessage", request.getParameter("errorMessage"));
        request.setAttribute("successMessage", request.getParameter("successMessage"));
        String address = "/pages/common/errors/error.jsp";
        try {
            processRequest(request);
            address = getSuccessAddress(request);
        } catch (Exception e) {
            getLogger().warn(getErrorMessage(request), e);
            request.setAttribute("errorMessage", getErrorMessage(request));
        }
        WebUtil.forwardToJsp(request, response, address);
    }
}

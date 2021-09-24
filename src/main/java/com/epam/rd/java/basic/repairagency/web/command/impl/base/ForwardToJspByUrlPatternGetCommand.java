package com.epam.rd.java.basic.repairagency.web.command.impl.base;

import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ProcessUrlPatterns({"/login",
        "/registration",
        "/customer/repair-request/new"})
@ProcessMethods(Method.GET)
public class ForwardToJspByUrlPatternGetCommand implements Command {

    private static final Logger log = LogManager.getLogger(ForwardToJspByUrlPatternGetCommand.class);

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("{}: {}", request.getMethod(), WebUtil.getUrlPattern(request));
        request.setAttribute("errorMessage", request.getParameter("errorMessage"));
        String address = "/pages/" + WebUtil.getUrlPattern(request) + ".jsp";
        WebUtil.forwardToJsp(request, response, address);
    }

}

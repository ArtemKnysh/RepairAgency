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

@ProcessUrlPatterns({"/admin/user/list",
        "/customer/repair-request/list",
        "/customer/user/list",
        "/manager/customer/view",
        "/manager/master/view",
        "/manager/repair-request/list",
        "/manager/repair-request/view",
        "/master/customer/view",
        "/master/repair-request/list",
        "/master/repair-request/view"})
@ProcessMethods(Method.POST)
public class RedirectToUrlPatternPostCommand implements Command {

    private static final Logger log = LogManager.getLogger(RedirectToUrlPatternPostCommand.class);

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("{}: {}", request.getMethod(), WebUtil.getUrlPattern(request));
        String address = WebUtil.getAppName(request) + WebUtil.getUrlPattern(request);
        response.sendRedirect(address);
    }

}

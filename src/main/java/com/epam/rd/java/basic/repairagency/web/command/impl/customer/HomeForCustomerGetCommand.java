package com.epam.rd.java.basic.repairagency.web.command.impl.customer;

import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@ProcessUrlPatterns("/customer/home")
@ProcessMethods(Method.GET)
public class HomeForCustomerGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(HomeForCustomerGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "Can't show " + role + " home page";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "/" + role + "/repair-request/list";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {

    }
}

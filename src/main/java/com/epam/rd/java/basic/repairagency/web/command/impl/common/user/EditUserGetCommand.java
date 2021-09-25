package com.epam.rd.java.basic.repairagency.web.command.impl.common.user;

import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@ProcessUrlPatterns("/common/user/edit")
@ProcessMethods(Method.GET)
public class EditUserGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(EditUserGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "Can't show edit " + role + " data page";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "/pages/" + role + "/user/edit.jsp";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
    }
}

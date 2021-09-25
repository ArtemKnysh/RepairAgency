package com.epam.rd.java.basic.repairagency.web.command.impl.common;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ProcessUrlPatterns("/registration")
@ProcessMethods(Method.POST)
public class RegistrationPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(RegistrationPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.empty();
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Registration wasn't complete. Please try again";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        User newUser = WebUtil.parseUserFromRequest(request);
        if (!newUser.isValid()) {
            throw new IllegalArgumentException("Some of the fields was incorrect");
        }
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        userService.insert(newUser);
        request.getSession().setAttribute("loggedUser", newUser);
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request);
    }
}

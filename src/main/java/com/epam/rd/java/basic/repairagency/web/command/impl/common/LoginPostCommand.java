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

@ProcessUrlPatterns("/login")
@ProcessMethods(Method.POST)
public class LoginPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(LoginPostCommand.class);

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
        return "Invalid email or password";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws NotFoundException, DBException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        User user = userService.findByEmailAndPassword(email, password);
        request.getSession().setAttribute("loggedUser", user);
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request);
    }
}

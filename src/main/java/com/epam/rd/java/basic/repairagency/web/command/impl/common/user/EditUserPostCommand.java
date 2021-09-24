package com.epam.rd.java.basic.repairagency.web.command.impl.common.user;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@ProcessUrlPatterns("/common/user/edit")
@ProcessMethods(Method.POST)
public class EditUserPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(EditUserPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("User data was successfully saved");
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Edit user data wasn't complete. Please try again";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException {
        User user = WebUtil.parseUserFromRequest(request);
        if (!user.isValid()) {
            throw new IllegalArgumentException("Some of the fields was incorrect");
        }
        if (user.getId() != WebUtil.getLoggedUser(request).getId()) {
            throw new IllegalArgumentException("Allowed to edit only logged user data");
        }
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        userService.update(user);
    }

}

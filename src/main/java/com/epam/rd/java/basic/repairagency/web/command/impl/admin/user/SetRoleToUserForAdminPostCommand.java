package com.epam.rd.java.basic.repairagency.web.command.impl.admin.user;


import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.exception.DBException;
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

@ProcessUrlPatterns("/admin/user/set-role")
@ProcessMethods(Method.POST)
public class SetRoleToUserForAdminPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(SetRoleToUserForAdminPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("User role was successfully set");
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "User role wasn't set. Please try again";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException {
        long userId = Long.parseLong(request.getParameter("userId"));
        UserRole role = UserRole.valueOf(UserRole.class, request.getParameter("role"));
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        userService.setRoleToUser(userId, role);
    }

    @Override
    protected String getErrorAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/admin/user/list";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/admin/user/list";
    }
}

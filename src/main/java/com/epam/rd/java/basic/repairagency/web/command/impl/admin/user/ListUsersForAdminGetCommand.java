package com.epam.rd.java.basic.repairagency.web.command.impl.admin.user;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/admin/user/list")
@ProcessMethods(Method.GET)
public class ListUsersForAdminGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ListUsersForAdminGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show users list page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        List<User> listUser = userService.findAll();
        request.setAttribute("listUser", listUser);
    }
}

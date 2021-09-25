package com.epam.rd.java.basic.repairagency.web.command.impl.admin.user;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.entity.sorting.UserSortingParameter;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/admin/user/list")
@ProcessMethods(Method.GET)
public class ListUsersForAdminGetCommand extends GetListCommand {

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
    protected int getCountOfEntities(HttpServletRequest request) throws DBException {
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        return userService.findCountOfUsersExcludeRoles(UserRole.ADMIN);
    }

    @Override
    protected String getDefaultSortingParameter() {
        return "firstName";
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                               SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException {
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        UserSortingParameter userSortingParameter = UserSortingParameter.getByFieldName(sortingParameter);
        return userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN}, offset, rowsNumber, userSortingParameter, sortingType);
    }
}

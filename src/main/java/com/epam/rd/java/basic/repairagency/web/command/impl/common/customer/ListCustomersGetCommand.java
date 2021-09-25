package com.epam.rd.java.basic.repairagency.web.command.impl.common.customer;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.User;
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
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/common/customer/list")
@ProcessMethods(Method.GET)
public class ListCustomersGetCommand extends GetListCommand {

    private static final Logger log = LogManager.getLogger(ListCustomersGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show customers list page";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "/pages/" + role + "/customer/list.jsp";
    }

    protected int getCountOfEntities(HttpServletRequest request) throws DBException {
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        return userService.findCountOfUsersByRole(UserRole.CUSTOMER);
    }

    protected String getDefaultSortingParameter() {
        return "firstName";
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber, SortingType sortingType, String sortingParameter) throws NotFoundException, DBException {
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        UserSortingParameter userSortingParameter = UserSortingParameter.getByFieldName(sortingParameter);
        return userService.findAllByRole(UserRole.CUSTOMER, offset, rowsNumber, userSortingParameter, sortingType);
    }

}

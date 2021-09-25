package com.epam.rd.java.basic.repairagency.web.command.impl.common.user;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.sorting.AccountTransactionSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.AccountTransactionService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListWithSortingAndPaginationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/common/user/view")
@ProcessMethods(Method.GET)
public class ViewUserGetCommand extends GetListWithSortingAndPaginationCommand {

    private static final Logger log = LogManager.getLogger(ViewUserGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "Can't show " + role + " details page";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "/pages/" + role + "/user/view.jsp";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        super.processRequest(request);
        User user = WebUtil.getLoggedUser(request);
        AccountTransactionService accountTransactionService = (AccountTransactionService)
                WebUtil.getService(request, AccountTransactionService.class);
        double balance = accountTransactionService.findSumOfAmountByUserId(user.getId());
        request.setAttribute("balance", balance);
    }

    @Override
    protected int getCountOfEntities(HttpServletRequest request) throws DBException {
        AccountTransactionService accountTransactionService = (AccountTransactionService)
                WebUtil.getService(request, AccountTransactionService.class);
        return accountTransactionService.findCountOfTransactionsByUserId(WebUtil.getLoggedUser(request).getId());
    }

    @Override
    protected String getDefaultSortingParameter() {
        return AccountTransactionSortingParameter.CREATED_AT.getFieldName();
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                               SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException {
        AccountTransactionService accountTransactionService = (AccountTransactionService)
                WebUtil.getService(request, AccountTransactionService.class);
        AccountTransactionSortingParameter accountTransactionSortingParameter;
        accountTransactionSortingParameter = AccountTransactionSortingParameter.getByFieldName(sortingParameter);
        return accountTransactionService.findAllByUserId(WebUtil.getLoggedUser(request).getId(), offset, rowsNumber,
                accountTransactionSortingParameter, sortingType);
    }
}

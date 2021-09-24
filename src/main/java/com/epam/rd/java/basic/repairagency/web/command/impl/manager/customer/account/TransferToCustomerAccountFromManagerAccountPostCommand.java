package com.epam.rd.java.basic.repairagency.web.command.impl.manager.customer.account;

import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.service.AccountTransactionService;
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

@ProcessUrlPatterns("/manager/customer/account/transfer")
@ProcessMethods(Method.POST)
public class TransferToCustomerAccountFromManagerAccountPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(TransferToCustomerAccountFromManagerAccountPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Transferring to customer account was successfully completed");
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't transfer to customer account";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        String address = WebUtil.getAppName(request) + "/manager/customer/view";
        address += "?customerId=" + request.getParameter("customerId");
        return address;
    }

    @Override
    protected String getErrorAddress(HttpServletRequest request) {
        String address = WebUtil.getAppName(request) + "/manager/customer/view";
        address += "?customerId=" + request.getParameter("customerId");
        return address;
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        long customerId = Long.parseLong(request.getParameter("customerId"));
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        if (userService.findById(customerId).getRole() != UserRole.CUSTOMER) {
            throw new IllegalArgumentException("Requested user with id '" + customerId + "' isn't a " + UserRole.CUSTOMER);
        }
        double amount = Double.parseDouble(request.getParameter("amount"));
        long userId = WebUtil.getLoggedUser(request).getId();
        AccountTransactionService accountTransactionService = (AccountTransactionService)
                WebUtil.getService(request, AccountTransactionService.class);
        accountTransactionService.transferBetweenAccounts(userId, customerId, amount);
    }
}

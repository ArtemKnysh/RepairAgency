package com.epam.rd.java.basic.repairagency.web.command.impl.customer.repairrequest;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.AccountTransactionService;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommandWithRedirectionToReferer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@ProcessUrlPatterns("/customer/repair-request/pay")
@ProcessMethods(Method.POST)
public class PayRepairRequestForCustomerCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(PayRepairRequestForCustomerCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getSuccessMessage(HttpServletRequest request) {
        return "Repair request was successfully paid";
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't pay for repair request";
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return "/customer/repair-request/view?";        //todo change to list
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        AccountTransactionService accountTransactionService = (AccountTransactionService)
                WebUtil.getService(request, AccountTransactionService.class);
        RepairRequest repairRequest = repairRequestService.findById(repairRequestId);
        long userId = WebUtil.getLoggedUser(request).getId();
        if (repairRequest.getCustomerId() != userId) {
            throw new IllegalArgumentException("Requested repair request with id '" + repairRequestId + "' doesn't belong " +
                    "to logged customer");
        }
        double userBalance = accountTransactionService.findSumOfAmountByUserId(userId);
        if (userBalance < repairRequest.getCost()) {
            throw new IllegalStateException("Can't pay for repair request with id '" + repairRequestId + "' " +
                    "by user with id '" + userId + "'. User balance (" + userBalance + ") is lower then " +
                    "repair request cost (" + repairRequest.getCost() + ")");
        }
        if (repairRequest.getStatus() != RepairRequestStatus.WAIT_FOR_PAYMENT) {
            throw new DBException("Can't pay for repair request with id '" + repairRequestId + "' " +
                    "by user with id '" + userId + "'. Repair request status (" + repairRequest.getStatus() + ") " +
                    "isn't equal to " + RepairRequestStatus.WAIT_FOR_PAYMENT);
        }
        accountTransactionService.payForRepairRequest(userId, repairRequestId);
    }
}

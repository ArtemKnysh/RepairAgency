package com.epam.rd.java.basic.repairagency.web.command.impl.customer.customer;

import com.epam.rd.java.basic.repairagency.entity.*;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.AccountTransactionService;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.service.GenericService;
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

@ProcessUrlPatterns("/customer/customer/view")
@ProcessMethods(Method.GET)
public class ViewCustomerForCustomerGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ViewCustomerForCustomerGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show customer details page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long customerId = Long.parseLong(request.getParameter("customerId"));
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        User customer = userService.findCustomerById(customerId);
        request.setAttribute("customer", customer);
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        List<Feedback> customerFeedbacks = feedbackService.findAllByCustomerId(customerId);
        request.setAttribute("customerFeedbacks", customerFeedbacks);
    }
}

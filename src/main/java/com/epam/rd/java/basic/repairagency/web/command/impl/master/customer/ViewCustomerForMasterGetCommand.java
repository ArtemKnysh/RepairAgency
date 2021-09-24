package com.epam.rd.java.basic.repairagency.web.command.impl.master.customer;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
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

@ProcessUrlPatterns("/master/customer/view")
@ProcessMethods(Method.GET)
public class ViewCustomerForMasterGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ViewCustomerForMasterGetCommand.class);

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
        long masterId = WebUtil.getLoggedUser(request).getId();
        long customerId = Long.parseLong(request.getParameter("customerId"));
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        User customer = userService.findCustomerById(customerId);
        request.setAttribute("customer", customer);
        RepairRequestService repairRequestService = (RepairRequestService) WebUtil.getService(request, RepairRequestService.class);
        List<RepairRequest> customerRepairRequests = repairRequestService.findAllByCustomerIdAndMasterIdAndStatusMoreThenPaid(customerId, masterId);
        request.setAttribute("customerRepairRequests", customerRepairRequests);
        try {
            FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
            Feedback customerFeedback = feedbackService.findByCustomerIdAndMasterId(customerId, masterId);
            request.setAttribute("customerFeedback", customerFeedback);
        } catch (Exception ignored) {}
    }
}

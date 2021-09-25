package com.epam.rd.java.basic.repairagency.web.command.impl.customer.master;


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

@ProcessUrlPatterns("/customer/master/view")
@ProcessMethods(Method.GET)
public class ViewMasterForCustomerGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ViewMasterForCustomerGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show master details page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long masterId = Long.parseLong(request.getParameter("masterId"));
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        User master = userService.findMasterById(masterId);
        request.setAttribute("master", master);
        long userId = WebUtil.getLoggedUser(request).getId();
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        List<RepairRequest> masterRepairRequests = repairRequestService
                .findAllByCustomerIdAndMasterIdAndStatusMoreThenPaid(userId, master.getId());
        request.setAttribute("masterRepairRequests", masterRepairRequests);

        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        if (repairRequestService.findAllByCustomerIdAndMasterIdAndStatusCompleted(userId, master.getId()).size() > 0) {
            try {
                Feedback feedback = feedbackService.findByCustomerIdAndMasterId(userId, masterId);
                request.setAttribute("isCustomerCanLeaveFeedbackForMaster", true);
                request.setAttribute("customerFeedback", feedback);
            } catch (Exception e) {
                request.setAttribute("isCustomerCanLeaveFeedbackForMaster", false);
                request.setAttribute("customerFeedback", new Feedback());
            }
        } else {
            request.setAttribute("isCustomerCanLeaveFeedbackForMaster", false);
            request.setAttribute("customerFeedback", new Feedback());
        }
        List<Feedback> masterFeedbacks = feedbackService.findAllByMasterIdExceptCustomerId(masterId, userId);
        request.setAttribute("masterFeedbacks", masterFeedbacks);
        if (request.getParameter("activeTab") == null) {
            request.setAttribute("activeTab", "repairRequests");
        } else {
            request.setAttribute("activeTab", request.getParameter("activeTab"));
        }
    }
}

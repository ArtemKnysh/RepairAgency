package com.epam.rd.java.basic.repairagency.web.command.impl.customer.feedback;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommandWithRedirectionToReferer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ProcessUrlPatterns("/customer/feedback/edit")
@ProcessMethods(Method.POST)
public class EditFeedbackTextForCustomerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(EditFeedbackTextForCustomerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getSuccessMessage(HttpServletRequest request) {
        return "Feedback text was successfully saved";
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't save feedback text";
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/customer/feedback/list";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long masterId = Long.parseLong(request.getParameter("masterId"));
        String text = request.getParameter("text");
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        Feedback feedback = new Feedback();
        feedback.setText(text);
        feedback.setMasterId(masterId);
        feedback.setCustomerId(WebUtil.getLoggedUser(request).getId());
        String feedbackIdAsString = request.getParameter("feedbackId");
        if (feedbackIdAsString != null && !feedbackIdAsString.trim().isEmpty()) {
            long feedbackId = Long.parseLong(feedbackIdAsString);
            feedback.setId(feedbackId);
            feedbackService.update(feedback);
        } else {
            feedbackService.insert(feedback);
        }
    }

    @Override
    protected List<String> getAdditionalRefererParameters(HttpServletRequest request) {
        List<String> parameters = new ArrayList<>();
        if (request.getParameter("activeTab") != null) {
            parameters.add("activeTab=" + request.getParameter("activeTab"));
        }
        return parameters;
    }
}

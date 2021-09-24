package com.epam.rd.java.basic.repairagency.web.command.impl.manager.feedback;

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

@ProcessUrlPatterns("/manager/feedback/show")
@ProcessMethods(Method.POST)
public class ShowFeedbackForManagerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(ShowFeedbackForManagerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getSuccessMessage(HttpServletRequest request) {
        return "Feedback was successfully shown";
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show feedback";
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/manager/feedback/list";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long feedbackId = Long.parseLong(request.getParameter("feedbackId"));
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        feedbackService.show(feedbackId);
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

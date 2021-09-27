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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ProcessUrlPatterns("/manager/feedback/show")
@ProcessMethods(Method.POST)
public class ShowFeedbackForManagerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(ShowFeedbackForManagerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Feedback was successfully shown");
    }

    @Override
    protected Optional<String> getErrorMessage(HttpServletRequest request) {
        return Optional.of("Can't show feedback");
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/manager/feedback/list";
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws DBException, NotFoundException {
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

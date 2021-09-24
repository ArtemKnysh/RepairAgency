package com.epam.rd.java.basic.repairagency.web.command.impl.master.feedback;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/master/feedback/list")
@ProcessMethods(Method.GET)
public class ListFeedbacksForMasterGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ListFeedbacksForMasterGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show feedbacks list page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long masterId = WebUtil.getLoggedUser(request).getId();
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        List<Feedback> feedbacks = feedbackService.findAllByMasterId(masterId);
        request.setAttribute("feedbacks", feedbacks);
    }
}

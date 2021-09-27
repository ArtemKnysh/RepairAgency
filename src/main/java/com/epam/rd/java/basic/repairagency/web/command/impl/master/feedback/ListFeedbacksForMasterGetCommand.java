package com.epam.rd.java.basic.repairagency.web.command.impl.master.feedback;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListWithSortingAndPaginationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/master/feedback/list")
@ProcessMethods(Method.GET)
public class ListFeedbacksForMasterGetCommand extends GetListWithSortingAndPaginationCommand {

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
    protected int getCountOfEntities(HttpServletRequest request) throws DBException {
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        long masterId = WebUtil.getLoggedUser(request).getId();
        return feedbackService.findCountOfFeedbacksByMasterIdExcludeHidden(masterId);
    }

    @Override
    protected String getDefaultSortingParameter() {
        return "createdAt";
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                               SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException {
        long masterId = WebUtil.getLoggedUser(request).getId();
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        FeedbackSortingParameter feedbackSortingParameter = FeedbackSortingParameter.getByFieldName(sortingParameter);
        return feedbackService.findAllByMasterIdExcludeHidden(masterId, offset, rowsNumber, feedbackSortingParameter, sortingType);

    }
}

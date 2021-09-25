package com.epam.rd.java.basic.repairagency.web.command.impl.manager.feedback;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.filtering.FeedbackFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListWithSortingAndPaginationAndFilteringCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/manager/feedback/list")
@ProcessMethods(Method.GET)
public class ListFeedbacksForManagerGetCommand extends GetListWithSortingAndPaginationAndFilteringCommand {

    private static final Logger log = LogManager.getLogger(ListFeedbacksForManagerGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show feedbacks list page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        super.processRequest(request);
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        List<User> masters = userService.findAllByRole(UserRole.MASTER);
        request.setAttribute("listMasters", masters);
    }

    @Override
    protected int getCountOfEntitiesWithFilter(HttpServletRequest request, String filterName, String filterValue) throws DBException {
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        FeedbackFilterParameter feedbackFilterParameter = FeedbackFilterParameter.getByFieldName(filterName);
        return feedbackService.findCountOfFeedbacks(feedbackFilterParameter, filterValue);
    }

    @Override
    protected int getCountOfEntitiesWithoutFilter(HttpServletRequest request) throws DBException {
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        return feedbackService.findCountOfFeedbacks();
    }

    @Override
    protected String getDefaultSortingParameter() {
        return FeedbackSortingParameter.CREATED_AT.getFieldName();
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntitiesWithFilter(HttpServletRequest request, int offset,
                                                                         int rowsNumber, SortingType sortingType,
                                                                         String sortingParameter, String filterName,
                                                                         String filterValue
    ) throws NotFoundException, DBException {
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        FeedbackSortingParameter feedbackSortingParameter = FeedbackSortingParameter.getByFieldName(sortingParameter);
        FeedbackFilterParameter feedbackFilterParameter = FeedbackFilterParameter.getByFieldName(filterName);
        return feedbackService.findAll(offset, rowsNumber, feedbackSortingParameter, sortingType, feedbackFilterParameter, filterValue);
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntitiesWithoutFilter(HttpServletRequest request, int offset,
                                                                            int rowsNumber, SortingType sortingType,
                                                                            String sortingParameter
    ) throws DBException, NotFoundException {
        FeedbackService feedbackService = (FeedbackService) WebUtil.getService(request, FeedbackService.class);
        FeedbackSortingParameter feedbackSortingParameter = FeedbackSortingParameter.getByFieldName(sortingParameter);
        return feedbackService.findAll(offset, rowsNumber, feedbackSortingParameter, sortingType);
    }
}

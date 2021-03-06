package com.epam.rd.java.basic.repairagency.web.command.impl.manager.reapairrequest;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.filtering.RepairRequestFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.RepairRequestSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
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

@ProcessUrlPatterns("/manager/repair-request/list")
@ProcessMethods(Method.GET)
public class ListRepairRequestsForManagerGetCommand extends GetListWithSortingAndPaginationAndFilteringCommand {

    private static final Logger log = LogManager.getLogger(ListRepairRequestsForManagerGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show repair requests list page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        super.processRequest(request);
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        List<User> masters = userService.findAllByRole(UserRole.MASTER);
        request.setAttribute("listMasters", masters);
        request.setAttribute("statuses", RepairRequestStatus.values());
    }

    @Override
    protected int getCountOfEntitiesWithFilter(HttpServletRequest request, String filterName, String filterValue) throws DBException {
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        RepairRequestFilterParameter repairRequestFilterParameter = RepairRequestFilterParameter.getByFieldName(filterName);
        return repairRequestService.findCountOfRepairRequests(repairRequestFilterParameter, filterValue);
    }

    @Override
    protected int getCountOfEntitiesWithoutFilter(HttpServletRequest request) throws DBException {
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        return repairRequestService.findCountOfRepairRequests();
    }

    @Override
    protected String getDefaultSortingParameter() {
        return RepairRequestSortingParameter.CREATED_AT.getFieldName();
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntitiesWithFilter(HttpServletRequest request, int offset, int rowsNumber,
                                                                         SortingType sortingType, String sortingParameter,
                                                                         String filterName, String filterValue
    ) throws NotFoundException, DBException {
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        RepairRequestSortingParameter repairRequestSortingParameter = RepairRequestSortingParameter.getByFieldName(sortingParameter);
        RepairRequestFilterParameter repairRequestFilterParameter = RepairRequestFilterParameter.getByFieldName(filterName);
        return repairRequestService.findAll(offset, rowsNumber, repairRequestSortingParameter, sortingType, repairRequestFilterParameter, filterValue);
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntitiesWithoutFilter(HttpServletRequest request, int offset,
                                                                            int rowsNumber, SortingType sortingType,
                                                                            String sortingParameter) throws DBException, NotFoundException {
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        RepairRequestSortingParameter repairRequestSortingParameter = RepairRequestSortingParameter.getByFieldName(sortingParameter);
        return repairRequestService.findAll(offset, rowsNumber, repairRequestSortingParameter, sortingType);
    }
}

package com.epam.rd.java.basic.repairagency.web.command.impl.master.repairrequest;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.sorting.RepairRequestSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListWithSortingAndPaginationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/master/repair-request/list")
@ProcessMethods(Method.GET)
public class ListRepairRequestsForMasterGetCommand extends GetListWithSortingAndPaginationCommand {

    private static final Logger log = LogManager.getLogger(ListRepairRequestsForMasterGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show repair requests list page";
    }

    @Override
    protected int getCountOfEntities(HttpServletRequest request) throws DBException {
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        long masterId = WebUtil.getLoggedUser(request).getId();
        return repairRequestService.findCountOfRepairRequestsByMasterIdAndStatusMoreThenPaid(masterId);
    }

    @Override
    protected String getDefaultSortingParameter() {
        return RepairRequestSortingParameter.CREATED_AT.getFieldName();
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                               SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException {
        long masterId = WebUtil.getLoggedUser(request).getId();
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        RepairRequestSortingParameter repairRequestSortingParameter = RepairRequestSortingParameter.getByFieldName(sortingParameter);
        return repairRequestService.findAllByMasterIdAndStatusMoreThenPaid(masterId, offset, rowsNumber, repairRequestSortingParameter, sortingType);
    }
}

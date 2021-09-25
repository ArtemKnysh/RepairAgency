package com.epam.rd.java.basic.repairagency.web.command.impl.customer.repairrequest;

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
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetListCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/customer/repair-request/list")
@ProcessMethods(Method.GET)
public class ListRepairRequestsForCustomerGetCommand extends GetListCommand {

    private static final Logger log = LogManager.getLogger(ListRepairRequestsForCustomerGetCommand.class);

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
        long customerId = WebUtil.getLoggedUser(request).getId();
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        return repairRequestService.findCountOfRepairRequestsByCustomerId(customerId);
    }

    @Override
    protected String getDefaultSortingParameter() {
        return RepairRequestSortingParameter.STATUS.getFieldName();
    }

    @Override
    protected List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                               SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException {
        long customerId = WebUtil.getLoggedUser(request).getId();
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        RepairRequestSortingParameter repairRequestSortingParameter = RepairRequestSortingParameter.getByFieldName(sortingParameter);
        return repairRequestService.findAllByCustomerId(customerId, offset, rowsNumber, repairRequestSortingParameter, sortingType);
    }
}

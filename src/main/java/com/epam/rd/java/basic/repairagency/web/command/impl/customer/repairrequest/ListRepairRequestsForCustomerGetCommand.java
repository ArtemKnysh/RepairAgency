package com.epam.rd.java.basic.repairagency.web.command.impl.customer.repairrequest;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/customer/repair-request/list")
@ProcessMethods(Method.GET)
public class ListRepairRequestsForCustomerGetCommand extends GetCommand {

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
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long userId = WebUtil.getLoggedUser(request).getId();
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        List<RepairRequest> listRepairRequests = repairRequestService.findAllByCustomerId(userId);
        request.setAttribute("listRepairRequests", listRepairRequests);
    }
}
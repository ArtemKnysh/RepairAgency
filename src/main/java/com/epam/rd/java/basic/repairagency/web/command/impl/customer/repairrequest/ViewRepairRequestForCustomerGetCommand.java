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

@ProcessUrlPatterns("/customer/repair-request/view")
@ProcessMethods(Method.GET)
public class ViewRepairRequestForCustomerGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ViewRepairRequestForCustomerGetCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't show repair request details page";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        RepairRequest repairRequest = repairRequestService.findById(repairRequestId);
        if (repairRequest.getCustomerId() != WebUtil.getLoggedUser(request).getId()) {
            throw new IllegalArgumentException("Requested repair request with id '" + repairRequestId + "' doesn't belong " +
                    "to logged customer");
        }
        request.setAttribute("repairRequest", repairRequest);
    }
}

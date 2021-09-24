package com.epam.rd.java.basic.repairagency.web.command.impl.manager.reapairrequest;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.service.GenericService;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommandWithRedirectionToReferer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@ProcessUrlPatterns("/manager/repair-request/set-cost")
@ProcessMethods(Method.POST)
public class SetCostToRepairRequestForManagerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(SetCostToRepairRequestForManagerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getSuccessMessage(HttpServletRequest request) {
        return "Cost was successfully set";
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Cost wasn't set. Please try again";
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return "/manager/repair-request/list?";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        double cost = Double.parseDouble(request.getParameter("cost"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        repairRequestService.setCostToRepairRequest(repairRequestId, cost);
    }
}

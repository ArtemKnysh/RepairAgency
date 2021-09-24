package com.epam.rd.java.basic.repairagency.web.command.impl.manager.master;

import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommandWithRedirectionToReferer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@ProcessUrlPatterns("/manager/master/remove-repair-request")
@ProcessMethods(Method.POST)
public class RemoveRepairRequestFromMasterForManagerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(RemoveRepairRequestFromMasterForManagerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getSuccessMessage(HttpServletRequest request) {
        return "Repair request was successfully removed";
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Repair request wasn't removed. Please try again";
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return "/manager/master/list?";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        repairRequestService.removeMasterFromRepairRequest(repairRequestId);
    }

}

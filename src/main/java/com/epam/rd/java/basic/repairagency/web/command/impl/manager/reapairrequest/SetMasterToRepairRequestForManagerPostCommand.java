package com.epam.rd.java.basic.repairagency.web.command.impl.manager.reapairrequest;

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
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@ProcessUrlPatterns("/manager/repair-request/set-master")
@ProcessMethods(Method.POST)
public class SetMasterToRepairRequestForManagerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(SetMasterToRepairRequestForManagerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Master was successfully set");
    }

    @Override
    protected Optional<String> getErrorMessage(HttpServletRequest request) {
        return Optional.of("Master wasn't set. Please try again");
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return "/manager/repair-request/list?";
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws DBException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        long masterId = Long.parseLong(request.getParameter("masterId"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        if (masterId == 0) {
            repairRequestService.removeMasterFromRepairRequest(repairRequestId);
        } else {
            repairRequestService.setMasterToRepairRequest(repairRequestId, masterId);
        }
    }
}

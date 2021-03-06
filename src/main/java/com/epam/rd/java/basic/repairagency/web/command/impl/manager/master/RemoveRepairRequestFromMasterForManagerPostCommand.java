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
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@ProcessUrlPatterns("/manager/master/remove-repair-request")
@ProcessMethods(Method.POST)
public class RemoveRepairRequestFromMasterForManagerPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(RemoveRepairRequestFromMasterForManagerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Repair request was successfully removed");
    }

    @Override
    protected Optional<String> getErrorMessage(HttpServletRequest request) {
        return Optional.of("Repair request wasn't removed. Please try again");
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return "/manager/master/list?";
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws DBException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        repairRequestService.removeMasterFromRepairRequest(repairRequestId);
    }

}

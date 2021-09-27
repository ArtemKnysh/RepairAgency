package com.epam.rd.java.basic.repairagency.web.command.impl.common.repairrequest;

import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
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

@ProcessUrlPatterns({"/master/repair-request/set-status", "/manager/repair-request/set-status"})
@ProcessMethods(Method.POST)
public class SetStatusToRepairRequestPostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(SetStatusToRepairRequestPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Status was successfully set");
    }

    @Override
    protected Optional<String> getErrorMessage(HttpServletRequest request) {
        return Optional.of("Status wasn't set. Please try again");
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        String role = WebUtil.getLoggedUser(request).getRole().toString().toLowerCase();
        return "/" + role + "/repair-request/list?";
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws DBException {
        long repairRequestId = Long.parseLong(request.getParameter("repairRequestId"));
        RepairRequestStatus status = RepairRequestStatus.valueOf(RepairRequestStatus.class,
                request.getParameter("status"));
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        repairRequestService.setStatusToRepairRequest(repairRequestId, status);
    }
}

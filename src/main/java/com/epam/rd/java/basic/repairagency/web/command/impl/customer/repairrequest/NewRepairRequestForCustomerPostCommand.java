package com.epam.rd.java.basic.repairagency.web.command.impl.customer.repairrequest;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ProcessUrlPatterns("/customer/repair-request/new")
@ProcessMethods(Method.POST)
public class NewRepairRequestForCustomerPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(NewRepairRequestForCustomerPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Repair request was successfully created");
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Creating new repair request wasn't complete. Please try again";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws DBException, NotFoundException {
        RepairRequest newRepairRequest = new RepairRequest();
        newRepairRequest.setDescription(request.getParameter("description"));
        newRepairRequest.setCustomer(WebUtil.getLoggedUser(request));
        if (!newRepairRequest.isValid()) {
            throw new IllegalArgumentException("Some of the fields was incorrect");
        }
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        repairRequestService.insert(newRepairRequest);
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/customer/repair-request/list";
    }

    @Override
    protected String getErrorAddress(HttpServletRequest request) {
        return getSuccessAddress(request);
    }
}

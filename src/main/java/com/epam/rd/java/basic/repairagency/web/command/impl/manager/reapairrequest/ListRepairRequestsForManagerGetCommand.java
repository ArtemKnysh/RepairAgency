package com.epam.rd.java.basic.repairagency.web.command.impl.manager.reapairrequest;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.GetCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ProcessUrlPatterns("/manager/repair-request/list")
@ProcessMethods(Method.GET)
public class ListRepairRequestsForManagerGetCommand extends GetCommand {

    private static final Logger log = LogManager.getLogger(ListRepairRequestsForManagerGetCommand.class);

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
        RepairRequestService repairRequestService = (RepairRequestService)
                WebUtil.getService(request, RepairRequestService.class);
        List<RepairRequest> listRepairRequests = repairRequestService.findAll();
        request.setAttribute("listRepairRequests", listRepairRequests);
        UserService userService = (UserService) WebUtil.getService(request, UserService.class);
        List<User> masters = userService.findAllByRole(UserRole.MASTER);
        request.setAttribute("listMasters", masters);
    }
}

package com.epam.rd.java.basic.repairagency.web.command.impl.common.user.account;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;
import com.epam.rd.java.basic.repairagency.service.AccountTransactionService;
import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.command.impl.base.PostCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ProcessUrlPatterns("/common/user/account/top-up")
@ProcessMethods(Method.POST)
public class TopUpAccountPostCommand extends PostCommand {

    private static final Logger log = LogManager.getLogger(TopUpAccountPostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.of("Account top-up was successfully completed");
    }

    @Override
    protected String getErrorMessage(HttpServletRequest request) {
        return "Can't top-up account";
    }

    @Override
    protected String getSuccessAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/common/user/view";
    }

    @Override
    protected String getErrorAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/common/user/view";
    }

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        double amount = Double.parseDouble(request.getParameter("amount"));
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setUserId(WebUtil.getLoggedUser(request).getId());
        accountTransaction.setAmount(amount);
        AccountTransactionService accountTransactionService = (AccountTransactionService)
                WebUtil.getService(request, AccountTransactionService.class);
        accountTransactionService.insert(accountTransaction);
    }
}

package com.epam.rd.java.basic.repairagency.web.command.impl.locale;

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

@ProcessUrlPatterns("/locale/change")
@ProcessMethods(Method.POST)
public class ChangeLocalePostCommand extends PostCommandWithRedirectionToReferer {

    private static final Logger log = LogManager.getLogger(ChangeLocalePostCommand.class);

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected Optional<String> getSuccessMessage(HttpServletRequest request) {
        return Optional.empty();
    }

    @Override
    protected Optional<String> getErrorMessage(HttpServletRequest request) {
        return Optional.empty();
    }

    @Override
    protected String getDefaultAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + "/";
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute("lang", request.getParameter("lang"));
    }

}

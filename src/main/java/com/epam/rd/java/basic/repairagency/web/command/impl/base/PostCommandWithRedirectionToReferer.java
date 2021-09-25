package com.epam.rd.java.basic.repairagency.web.command.impl.base;

import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class PostCommandWithRedirectionToReferer implements Command {

    protected abstract Logger getLogger();

    protected abstract String getSuccessMessage(HttpServletRequest request);

    protected abstract String getErrorMessage(HttpServletRequest request);

    protected abstract String getDefaultAddress(HttpServletRequest request);

    protected abstract void processRequest(HttpServletRequest request) throws Exception;

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getLogger().info("{}: {}", request.getMethod(), WebUtil.getUrlPattern(request));
        StringBuilder address = new StringBuilder();
        try {
            String referer = request.getHeader("referer");
            getLogger().info("referer = " + referer);
            if (referer != null) {
                address.append(WebUtil.getUrlPatternWithParametersExceptMessages(referer));
                if (address.charAt(address.length() - 1) != '&') {
                    address.append("?");
                }
            } else {
                address.append(WebUtil.getAppName(request));
                address.append(getDefaultAddress(request));
            }
            processRequest(request);
            address.append("successMessage=").append(getSuccessMessage(request));
            for (String parameter : getAdditionalRefererParameters(request)) {
                address.append("&").append(parameter);
            }
        } catch (Exception e) {
            getLogger().warn(getErrorMessage(request), e);
            address.append("errorMessage=").append(getErrorMessage(request));
        }
        response.sendRedirect(address.toString());
    }

    protected List<String> getAdditionalRefererParameters(HttpServletRequest request) {
        return Collections.emptyList();
    }

}

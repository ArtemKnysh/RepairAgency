package com.epam.rd.java.basic.repairagency.web.command.impl.base;

import com.epam.rd.java.basic.repairagency.util.web.WebUtil;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public abstract class PostCommand implements Command {

    protected abstract Logger getLogger();

    protected abstract Optional<String> getSuccessMessage(HttpServletRequest request);

    protected abstract String getErrorMessage(HttpServletRequest request);

    protected String getErrorAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + WebUtil.getUrlPattern(request);
    }

    protected String getSuccessAddress(HttpServletRequest request) {
        return WebUtil.getAppName(request) + WebUtil.getUrlPattern(request);
    }

    protected abstract void processRequest(HttpServletRequest request) throws Exception;

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getLogger().info("{}: {}", request.getMethod(), WebUtil.getUrlPattern(request));
        String address = getErrorAddress(request);
        try {
            processRequest(request);
            address = getSuccessAddress(request);
            Optional<String> successMessage = getSuccessMessage(request);
            if (successMessage.isPresent()) {
                if (address.indexOf('?') != -1) {
                    address += '&';
                } else {
                    address += '?';
                }
                address += "successMessage=" + successMessage.get();
            }
        } catch (Exception e) {
            getLogger().warn(getErrorMessage(request), e);
            if (address.indexOf('?') != -1) {
                address += '&';
            } else {
                address += '?';
            }
            address += "errorMessage=" + getErrorMessage(request);
        }
        response.sendRedirect(address);
    }

}

package com.epam.rd.java.basic.repairagency.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DefaultLocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute("lang") == null) {
            req.getSession().setAttribute("lang", req.getServletContext().getInitParameter("defaultLang"));
        }
        chain.doFilter(request, response);
    }
}

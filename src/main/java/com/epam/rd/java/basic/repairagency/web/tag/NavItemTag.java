package com.epam.rd.java.basic.repairagency.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class NavItemTag extends SimpleTagSupport {

    private String href;
    private String active;
    private String text;

    public void setHref(String href) {
        this.href = href;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder result = new StringBuilder();
        result.append("<li class=\"nav-item\"><a class=\"nav-link");
        if (!String.valueOf(active).trim().isEmpty() && href.contains(active)) {
            result.append(" active");
        }
        result.append("\" href=\"")
                .append(href)
                .append("\">")
                .append(text)
                .append("</a></li>");
        getJspContext().getOut().print(result.toString());
    }
}

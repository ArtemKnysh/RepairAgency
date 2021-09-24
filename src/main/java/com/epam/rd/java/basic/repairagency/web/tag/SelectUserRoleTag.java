package com.epam.rd.java.basic.repairagency.web.tag;

import com.epam.rd.java.basic.repairagency.entity.UserRole;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SelectUserRoleTag extends SimpleTagSupport {

    private String role;

    private String selectId;

    public void setRole(String role) {
        this.role = role;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder result = new StringBuilder();
        result.append("<select class=\"form-control mb-2\" name=\"role\">");
        for (UserRole value : UserRole.values()) {
            if (value == UserRole.ADMIN) {
                continue;
            }
            result.append("<option value=\"")
                    .append(value).append("\"");
            if (role.equals(value.toString())) {
                result.append(" selected");
            }
            result.append(">")
                    .append(value)
                    .append("</option>");
        }
        result.append("</select>");
        getJspContext().getOut().print(result.toString());
    }
}

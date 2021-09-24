package com.epam.rd.java.basic.repairagency.web.tag;

import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SelectRepairRequestStatusTag extends SimpleTagSupport {

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder result = new StringBuilder();
        RepairRequestStatus status = RepairRequestStatus.valueOf(RepairRequestStatus.class, this.status);
        result.append("<select class=\"form-control mb-2\" name=\"status\">");
        result.append("<option value=\"").append(status).append("\"");
        result.append(">").append(status).append("</option>");
        for (RepairRequestStatus value : status.getNextStatuses()) {
            result.append("<option value=\"").append(value).append("\"");
            result.append(">").append(value).append("</option>");
        }
        result.append("</select>");
        getJspContext().getOut().print(result.toString());
    }
}

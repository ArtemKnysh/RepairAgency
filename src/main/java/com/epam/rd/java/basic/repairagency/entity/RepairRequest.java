package com.epam.rd.java.basic.repairagency.entity;

public class RepairRequest extends AbstractEntity {

    private RepairRequestStatus status;

    private String description;
    private User customer;
    private long customerId;
    private User master;
    private long masterId;
    private double cost;

    public RepairRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        if (description.length() > 29) {
            return description.substring(0, 29) + "...";
        } else {
            return description;
        }
    }

    @Override
    public boolean isValid() {
        return isNotBlank(description) && customerId != 0;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
        customerId = customer.getId();
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
        masterId = master.getId();
    }

    public RepairRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RepairRequestStatus status) {
        this.status = status;
    }

    public boolean isCanBeCancelled() {
        return getStatus().getId() < RepairRequestStatus.CANCELLED.getId();
    }

    @Override
    public String toString() {
        return "RepairRequest [" +
                "description='" + description + '\'' +
                ", customerId=" + customerId +
                ", masterId=" + masterId +
                ", cost=" + cost +
                ", status=" + status +
                ']';
    }
}

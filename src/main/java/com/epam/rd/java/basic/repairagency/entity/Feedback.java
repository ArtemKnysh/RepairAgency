package com.epam.rd.java.basic.repairagency.entity;

public class Feedback extends AbstractEntity {

    private String text;
    private boolean isHidden;
    private long customerId;
    private User customer;
    private long masterId;
    private User master;

    public Feedback() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
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

    @Override
    public String toString() {
        return "Feedback [" +
                "id='" + getId() + '\'' +
                "text='" + text + '\'' +
                ", isHidden=" + isHidden +
                ", customerId=" + customerId +
                ", masterId=" + masterId +
                ']';
    }
}

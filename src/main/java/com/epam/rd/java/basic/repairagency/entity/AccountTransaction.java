package com.epam.rd.java.basic.repairagency.entity;

import java.time.LocalDateTime;

public class AccountTransaction extends AbstractEntity {

    private long userId;
    private double amount;
    private LocalDateTime createdAt;

    public AccountTransaction() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountWithSign() {
        if (amount > 0) {
            return "+" + amount;
        } else {
            return String.valueOf(amount);
        }
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AccountTransaction [" +
                "id=" + getId() +
                "userId=" + userId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ']';
    }
}

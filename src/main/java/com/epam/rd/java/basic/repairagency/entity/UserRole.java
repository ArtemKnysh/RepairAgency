package com.epam.rd.java.basic.repairagency.entity;

public enum UserRole {
    CUSTOMER(1), MANAGER(2), MASTER(3), ADMIN(4);

    private long id;

    UserRole(long id) {
        this.id = id;
    }

    public static UserRole getById(long id) {
        for (UserRole value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find UserRole with id - " + id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

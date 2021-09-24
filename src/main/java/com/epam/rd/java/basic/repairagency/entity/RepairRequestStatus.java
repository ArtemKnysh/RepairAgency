package com.epam.rd.java.basic.repairagency.entity;

public enum RepairRequestStatus {

    CREATED(1), WAIT_FOR_PAYMENT(2), CANCELLED(3), PAID(4),
    GIVEN_TO_MASTER(5), IN_WORK(6), COMPLETED(7);

    private long id;

    RepairRequestStatus(long id) {
        this.id = id;
    }

    public static RepairRequestStatus getById(long id) {
        for (RepairRequestStatus value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find RepairRequestStatus with id - " + id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RepairRequestStatus[] getNextStatuses() {
        switch (this) {
            case CREATED:
                return new RepairRequestStatus[]{WAIT_FOR_PAYMENT, CANCELLED};
            case WAIT_FOR_PAYMENT:
                return new RepairRequestStatus[]{CANCELLED};
            case PAID:
                return new RepairRequestStatus[]{GIVEN_TO_MASTER};
            case GIVEN_TO_MASTER:
                return new RepairRequestStatus[]{IN_WORK};
            case IN_WORK:
                return new RepairRequestStatus[]{COMPLETED};
            default:
                return new RepairRequestStatus[0];
        }
    }
}

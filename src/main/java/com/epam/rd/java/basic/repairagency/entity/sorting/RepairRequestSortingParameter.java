package com.epam.rd.java.basic.repairagency.entity.sorting;

public enum RepairRequestSortingParameter {

    DESCRIPTION("description", "description"),
    STATUS("status", "status_id"),
    CREATED_AT("createdAt", "create_time"),
    MASTER_FULL_NAME("masterFullName", "master_full_name"),
    CUSTOMER_FULL_NAME("customerFullName", "customer_full_name"),
    COST("cost", "cost");

    private final String fieldName;
    private final String columnName;

    RepairRequestSortingParameter(String fieldName, String columnName) {
        this.fieldName = fieldName;
        this.columnName = columnName;
    }

    public static RepairRequestSortingParameter getByFieldName(String fieldName) {
        for (RepairRequestSortingParameter value : values()) {
            if (value.getFieldName().equals(fieldName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find repair request sorting parameter by field name " + fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }
}

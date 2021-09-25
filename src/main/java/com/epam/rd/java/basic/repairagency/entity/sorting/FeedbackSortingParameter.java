package com.epam.rd.java.basic.repairagency.entity.sorting;

public enum FeedbackSortingParameter {

    CREATED_AT("createdAt", "create_time"),
    TEXT("text", "text"),
    IS_HIDDEN("isHidden", "is_hidden"),
    MASTER_FULL_NAME("masterFullName", "master_full_name"),
    CUSTOMER_FULL_NAME("customerFullName", "customer_full_name");

    private final String fieldName;
    private final String columnName;

    FeedbackSortingParameter(String fieldName, String columnName) {
        this.fieldName = fieldName;
        this.columnName = columnName;
    }

    public static FeedbackSortingParameter getByFieldName(String fieldName) {
        for (FeedbackSortingParameter value : values()) {
            if (value.getFieldName().equals(fieldName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find feedback sorting parameter by field name " + fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }
}

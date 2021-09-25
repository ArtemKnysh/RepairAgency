package com.epam.rd.java.basic.repairagency.entity.sorting;

public enum UserSortingParameter {

    CREATED_AT("createdAt", "create_time"),
    FIRST_NAME("firstName", "first_name"),
    LAST_NAME("lastName", "last_name"),
    EMAIL("email", "email"),
    PHONE_NUMBER("phoneNumber", "phone_number");

    private final String fieldName;
    private final String columnName;

    UserSortingParameter(String fieldName, String columnName) {
        this.fieldName = fieldName;
        this.columnName = columnName;
    }

    public static UserSortingParameter getByFieldName(String fieldName) {
        for (UserSortingParameter value : values()) {
            if (value.getFieldName().equals(fieldName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find user sorting parameter by field name" + fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }
}

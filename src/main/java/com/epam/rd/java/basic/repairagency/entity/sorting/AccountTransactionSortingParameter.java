package com.epam.rd.java.basic.repairagency.entity.sorting;

public enum AccountTransactionSortingParameter {

    CREATED_AT("createdAt", "create_time"),
    AMOUNT("amount", "amount");

    private final String fieldName;
    private final String columnName;

    AccountTransactionSortingParameter(String fieldName, String columnName) {
        this.fieldName = fieldName;
        this.columnName = columnName;
    }

    public static AccountTransactionSortingParameter getByFieldName(String fieldName) {
        for (AccountTransactionSortingParameter value : values()) {
            if (value.getFieldName().equals(fieldName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find account transaction sorting parameter by field name " + fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }
}

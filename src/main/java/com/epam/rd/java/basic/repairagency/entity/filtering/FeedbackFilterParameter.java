package com.epam.rd.java.basic.repairagency.entity.filtering;

import java.util.function.Function;

public enum FeedbackFilterParameter {

    IS_HIDDEN("isHidden", "is_hidden", Boolean::parseBoolean),
    MASTER_ID("masterId", "master_id", Long::parseLong);

    private final String fieldName;
    private final String columnName;
    private final Function<String, Object> getValueFromString;

    FeedbackFilterParameter(String fieldName, String columnName, Function<String, Object> getValueFromString) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.getValueFromString = getValueFromString;
    }

    public static FeedbackFilterParameter getByFieldName(String fieldName) {
        for (FeedbackFilterParameter value : values()) {
            if (value.getFieldName().equals(fieldName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Can't find feedback filter parameter by field name " + fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue(String filterValue) {
        return getValueFromString.apply(filterValue);
    }
}

package com.epam.rd.java.basic.repairagency.entity.sorting;

public enum SortingType {
    DESC("DESC"),
    ASC("ASC");

    private final String type;

    SortingType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SortingType getSortingType(String type) {
        if (type == null) {
            return DESC;
        }
        type = type.trim().toUpperCase();
        for (SortingType value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return DESC;
    }

    public static SortingType reverse(SortingType sort) {
        return ASC.equals(sort) ? DESC : ASC;
    }
}

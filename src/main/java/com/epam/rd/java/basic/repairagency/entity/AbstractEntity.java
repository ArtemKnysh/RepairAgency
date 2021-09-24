package com.epam.rd.java.basic.repairagency.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractEntity implements Serializable {

    private long id;
    private LocalDateTime createdAt;

    public AbstractEntity() {
    }

    public AbstractEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return id == abstractEntity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public boolean isValid() {
        return true;
    };
}

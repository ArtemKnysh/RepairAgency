package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.util.List;

public interface GenericService<E extends AbstractEntity> {

    void insert(E entity) throws DBException, NotFoundException;

    E findById(long id) throws DBException, NotFoundException;

    void update(E entity) throws DBException;

    void delete(E entity) throws DBException;

    List<E> findAll() throws DBException, NotFoundException;

}

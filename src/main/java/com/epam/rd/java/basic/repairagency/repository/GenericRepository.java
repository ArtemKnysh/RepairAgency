package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<E extends AbstractEntity> {

    void insert(Connection connection, E entity) throws SQLException, NotFoundException;

    E findById(Connection connection, long id) throws SQLException, NotFoundException;

    void update(Connection connection, E entity) throws SQLException;

    void delete(Connection connection, E entity) throws SQLException;

    List<E> findAll(Connection connection) throws SQLException, NotFoundException;

}

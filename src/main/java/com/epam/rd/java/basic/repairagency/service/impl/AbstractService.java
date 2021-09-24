package com.epam.rd.java.basic.repairagency.service.impl;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.service.GenericService;
import com.epam.rd.java.basic.repairagency.util.db.ConnectionManager;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractService<E extends AbstractEntity> implements GenericService<E> {

    private ConnectionManager connectionManager;

    protected AbstractService() {
    }

    protected abstract GenericRepository<E> getRepository();

    protected Connection getConnection() throws SQLException {
        if (connectionManager == null) {
            connectionManager = ConnectionManager.getInstance();
        }
        return connectionManager.getConnection();
    }

    @Override
    public void insert(E entity) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            getRepository().insert(connection, entity);
        } catch (SQLException e) {
            throw new DBException("Can't insert entity '" + entity + "' into DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public E findById(long id) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return getRepository().findById(connection, id);
        } catch (SQLException e) {
            throw new DBException("Can't find entity with id '" + id + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<E> findAll() throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return getRepository().findAll(connection);
        } catch (SQLException e) {
            throw new DBException("Can't find entities in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void update(E entity) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            getRepository().update(connection, entity);
        } catch (SQLException e) {
            throw new DBException("Can't update entity '" + entity + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void delete(E entity) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            getRepository().delete(connection, entity);
        } catch (SQLException e) {
            throw new DBException("Can't delete entity '" + entity + "' from DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

}

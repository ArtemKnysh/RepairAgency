package com.epam.rd.java.basic.repairagency.repository.impl;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.*;
import java.util.List;

public abstract class AbstractRepository<E extends AbstractEntity> implements GenericRepository<E> {

    protected abstract String getSelectQuery();

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract List<E> parseEntitiesFromResultSet(ResultSet rs) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, E entity) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, E entity) throws SQLException;

    protected void findDefaultDependencies(Connection connection, E entity) throws SQLException, NotFoundException {

    }

    protected void insertDefaultDependencies(Connection connection, E entity) throws SQLException {

    }

    @Override
    public void insert(Connection connection, E entity) throws SQLException {
        String sql = getInsertQuery();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepareStatementForInsert(statement, entity);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On insert was modified more then 1 record");
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                entity.setId(id);
            }
            insertDefaultDependencies(connection, entity);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    @Override
    public void update(Connection connection, E entity) throws SQLException {
        String sql = getUpdateQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            prepareStatementForUpdate(statement, entity);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On update was modified more then 1 record: " + count);
            }
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public void delete(Connection connection, E entity) throws SQLException {
        String sql = getDeleteQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
            connection.commit();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public E findById(Connection connection, long id) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try {
            return findByQueryWithParameters(connection, sql, id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Entity with id '" + id + "' wasn't found", nfe);
        }
    }

    @Override
    public List<E> findAll(Connection connection) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        return findAllByQueryWithoutParameters(connection, sql);
    }

    private E parseEntityFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {
        List<E> result = parseEntitiesFromResultSet(resultSet);
        if (result == null || result.size() == 0) {
            throw new NotFoundException("Entity wasn't found");
        }
        if (result.size() > 1) {
            throw new SQLException("Received more than one record");
        }
        return result.get(0);
    }

    private void prepareStatementWithParameters(PreparedStatement statement, Object firstParameter, Object... otherParameters) throws SQLException {
        int parameterIndex = 0;
        statement.setObject(++parameterIndex, firstParameter);
        for (Object otherParameter : otherParameters) {
            statement.setObject(++parameterIndex, otherParameter);
        }
    }

    protected E findByQueryWithParameters(Connection connection, String query, Object firstParameter, Object... otherParameters
    ) throws SQLException, NotFoundException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            prepareStatementWithParameters(statement, firstParameter, otherParameters);
            resultSet = statement.executeQuery();
            E entity = parseEntityFromResultSet(resultSet);
            findDefaultDependencies(connection, entity);
            return entity;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    protected E findByQueryWithoutParameters(Connection connection, String query) throws SQLException, NotFoundException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            E entity = parseEntityFromResultSet(resultSet);
            findDefaultDependencies(connection, entity);
            return entity;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    protected List<E> findAllByQueryWithParameters(Connection connection, String query, Object firstParameter,
                                                   Object... otherParameters
    ) throws SQLException, NotFoundException {
        List<E> result;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            prepareStatementWithParameters(statement, firstParameter, otherParameters);
            resultSet = statement.executeQuery();
            result = parseEntitiesFromResultSet(resultSet);
            for (E entity : result) {
                findDefaultDependencies(connection, entity);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    protected List<E> findAllByQueryWithoutParameters(Connection connection, String query) throws SQLException, NotFoundException {
        List<E> result;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            result = parseEntitiesFromResultSet(resultSet);
            for (E entity : result) {
                findDefaultDependencies(connection, entity);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    protected int findIntValueByQueryWithParameters(Connection connection, String sql, Object firstParameter,
                                                    Object... otherParameters) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            prepareStatementWithParameters(statement, firstParameter, otherParameters);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    protected int findIntValueByQueryWithoutParameters(Connection connection, String sql) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    protected double findDoubleValueByQueryWithParameters(Connection connection, String sql, Object firstParameter,
                                                          Object... otherParameters) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            prepareStatementWithParameters(statement, firstParameter, otherParameters);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            } else {
                return 0;
            }
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }
}

package com.epam.rd.java.basic.repairagency.repository.impl;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Repository;
import com.epam.rd.java.basic.repairagency.repository.FeedbackRepository;
import com.epam.rd.java.basic.repairagency.repository.UserRepository;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository(FeedbackRepository.class)
public class FeedbackRepositoryImpl extends AbstractRepository<Feedback> implements FeedbackRepository {

    @Inject
    private UserRepository userRepository;

    private FeedbackRepositoryImpl() {
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM feedback";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO feedback (text, customer_id, master_id) VALUES (?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE feedback SET text = ?, customer_id = ?, master_id = ? WHERE id = ?;";
    }

    protected String getUpdateIsHiddenQuery() {
        return "UPDATE feedback SET is_hidden = ? WHERE id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE feedback WHERE id = ?;";
    }

    @Override
    protected List<Feedback> parseResultSet(ResultSet rs) throws SQLException {
        List<Feedback> result = new ArrayList<>();
        while (rs.next()) {
            Feedback feedback = new Feedback();
            feedback.setId(rs.getLong("id"));
            feedback.setText(rs.getString("text"));
            feedback.setHidden(rs.getBoolean("is_hidden"));
            feedback.setCustomerId(rs.getLong("customer_id"));
            feedback.setMasterId(rs.getLong("master_id"));
            feedback.setCreatedAt(rs.getTimestamp("create_time").toLocalDateTime());
            result.add(feedback);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Feedback entity) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, entity.getText());
        statement.setLong(++paramIndex, entity.getCustomerId());
        statement.setLong(++paramIndex, entity.getMasterId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Feedback entity) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, entity.getText());
        statement.setLong(++paramIndex, entity.getCustomerId());
        statement.setLong(++paramIndex, entity.getMasterId());
        statement.setLong(++paramIndex, entity.getId());
    }

    private void prepareStatementForUpdateIsHidden(PreparedStatement statement, long feedbackId, boolean isHidden) throws SQLException {
        int paramIndex = 0;
        statement.setBoolean(++paramIndex, isHidden);
        statement.setLong(++paramIndex, feedbackId);
    }

    @Override
    protected void findDefaultDependencies(Connection connection, Feedback feedback) throws SQLException, NotFoundException {
        feedback.setCustomer(userRepository.findById(connection, feedback.getCustomerId()));
        feedback.setMaster(userRepository.findById(connection, feedback.getMasterId()));
    }

    @Override
    public List<Feedback> findAll(Connection connection) throws SQLException, NotFoundException {
        String sql = getSelectQuery() + " ORDER BY create_time DESC";
        return findAll(connection, sql);
    }

    @Override
    public Feedback findByCustomerIdAndMasterId(Connection connection, long customerId, long masterId) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? AND master_id = ?";
        List<Feedback> result;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, customerId);
            statement.setLong(2, masterId);
            resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            if (result == null || result.size() == 0) {
                throw new NotFoundException("Feedback with customerId '" + customerId + "' " +
                        "and masterId '" + masterId + "' wasn't found");
            }
            if (result.size() > 1) {
                throw new SQLException("Received more than one record");
            }
            Feedback feedback = result.get(0);
            findDefaultDependencies(connection, feedback);
            return feedback;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    @Override
    public List<Feedback> findAllByMasterIdExceptCustomerId(Connection connection, long masterId, long customerId)
            throws SQLException, NotFoundException {
        List<Feedback> result;
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? AND customer_id != ? AND is_hidden = false ORDER BY create_time DESC";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, masterId);
            statement.setLong(2, customerId);
            resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            for (Feedback feedback : result) {
                findDefaultDependencies(connection, feedback);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    @Override
    public List<Feedback> findAllByCustomerId(Connection connection, long customerId) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? AND is_hidden = false ORDER BY create_time DESC";
        return findAllByQueryWithOneParameter(connection, sql, customerId);
    }

    @Override
    public List<Feedback> findAllByCustomerIdIncludeHidden(Connection connection, long customerId) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? ORDER BY create_time DESC";
        return findAllByQueryWithOneParameter(connection, sql, customerId);
    }

    @Override
    public List<Feedback> findAllByMasterId(Connection connection, long masterId) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? AND is_hidden = false ORDER BY create_time DESC";
        return findAllByQueryWithOneParameter(connection, sql, masterId);
    }

    @Override
    public List<Feedback> findAllByMasterIdIncludeHidden(Connection connection, long masterId) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? ORDER BY create_time DESC";
        return findAllByQueryWithOneParameter(connection, sql, masterId);
    }

    protected List<Feedback> findAllByQueryWithOneParameter(Connection connection, String sql, long parameter) throws SQLException, NotFoundException {
        List<Feedback> result;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, parameter);
            resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            for (Feedback feedback : result) {
                findDefaultDependencies(connection, feedback);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    @Override
    public void hide(Connection connection, long feedbackId) throws SQLException {
        updateIsHidden(connection, feedbackId, true);
    }

    @Override
    public void show(Connection connection, long feedbackId) throws SQLException {
        updateIsHidden(connection, feedbackId, false);
    }

    private void updateIsHidden(Connection connection, long feedbackId, boolean isHidden) throws SQLException {
        String sql = getUpdateIsHiddenQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            prepareStatementForUpdateIsHidden(statement, feedbackId, isHidden);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On update was modified more then 1 record: " + count);
            }
        } finally {
            DBUtil.close(statement);
        }
    }
}

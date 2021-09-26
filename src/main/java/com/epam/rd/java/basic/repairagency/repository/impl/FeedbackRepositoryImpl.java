package com.epam.rd.java.basic.repairagency.repository.impl;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.filtering.FeedbackFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
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

    private String getSelectWithMasterAndCustomerFullNameQuery() {
        return "SELECT f.*," +
                " (SELECT CONCAT(c.first_name, ' ', c.last_name) FROM user c where c.id = f.customer_id) AS customer_full_name," +
                " (SELECT CONCAT(m.first_name, ' ', m.last_name) FROM user m where m.id = f.master_id) AS master_full_name" +
                " FROM feedback f";
    }

    private String getSelectCountQuery() {
        return "SELECT COUNT(*) FROM feedback";
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
    protected List<Feedback> parseEntitiesFromResultSet(ResultSet rs) throws SQLException {
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

    private void prepareStatementForUpdateIsHidden(PreparedStatement statement, long feedbackId, boolean isHidden
    ) throws SQLException {
        int paramIndex = 0;
        statement.setBoolean(++paramIndex, isHidden);
        statement.setLong(++paramIndex, feedbackId);
    }

    @Override
    protected void findDefaultDependencies(Connection connection, Feedback feedback
    ) throws SQLException, NotFoundException {
        feedback.setCustomer(userRepository.findById(connection, feedback.getCustomerId()));
        feedback.setMaster(userRepository.findById(connection, feedback.getMasterId()));
    }

    @Override
    public List<Feedback> findAll(Connection connection, int offset, int amount, FeedbackSortingParameter sortingParameter, SortingType sortingType) throws SQLException, NotFoundException {
        String query = getSelectWithMasterAndCustomerFullNameQuery();
        query += " ORDER BY " + sortingParameter.getColumnName();
        query += " " + sortingType.getType();
        query += " LIMIT " + offset + ", " + amount;
        return findAllByQueryWithoutParameters(connection, query);
    }

    @Override
    public List<Feedback> findAllByCustomerId(Connection connection, long customerId, int offset, int amount,
                                              FeedbackSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException {
        String query = getSelectWithMasterAndCustomerFullNameQuery();
        query += " WHERE f.customer_id = ? AND f.is_hidden = false";
        query += " ORDER BY " + sortingParam.getColumnName();
        query += " " + sortingType.getType();
        query += " LIMIT " + offset + ", " + amount;
        return findAllByQueryWithParameters(connection, query, customerId);
    }

    @Override
    public List<Feedback> findAllByMasterId(Connection connection, long masterId, int offset, int amount,
                                            FeedbackSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException {
        String query = getSelectWithMasterAndCustomerFullNameQuery();
        query += " WHERE f.master_id = ? AND f.is_hidden = false";
        query += " ORDER BY " + sortingParam.getColumnName();
        query += " " + sortingType.getType();
        query += " LIMIT " + offset + ", " + amount;
        return findAllByQueryWithParameters(connection, query, masterId);
    }

    @Override
    public List<Feedback> findAll(Connection connection) throws SQLException, NotFoundException {
        String sql = getSelectQuery() + " ORDER BY create_time DESC";
        return findAllByQueryWithoutParameters(connection, sql);
    }

    @Override
    public Feedback findByCustomerIdAndMasterId(Connection connection, long customerId, long masterId
    ) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? AND master_id = ? AND is_hidden = false";
        return findByQueryWithParameters(connection, sql, customerId, masterId);
    }

    @Override
    public List<Feedback> findAllByMasterIdExceptCustomerId(Connection connection, long masterId, long customerId)
            throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? AND customer_id != ? AND is_hidden = false ORDER BY create_time DESC";
        return findAllByQueryWithParameters(connection, sql, masterId, customerId);
    }

    @Override
    public List<Feedback> findAllByCustomerId(Connection connection, long customerId
    ) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? AND is_hidden = false ORDER BY create_time DESC";
        return findAllByQueryWithParameters(connection, sql, customerId);
    }

    @Override
    public List<Feedback> findAllByCustomerIdIncludeHidden(Connection connection, long customerId
    ) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? ORDER BY create_time DESC";
        return findAllByQueryWithParameters(connection, sql, customerId);
    }

    @Override
    public List<Feedback> findAllByMasterId(Connection connection, long masterId
    ) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? AND is_hidden = false ORDER BY create_time DESC";
        return findAllByQueryWithParameters(connection, sql, masterId);
    }

    @Override
    public List<Feedback> findAllByMasterIdIncludeHidden(Connection connection, long masterId
    ) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? ORDER BY create_time DESC";
        return findAllByQueryWithParameters(connection, sql, masterId);
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

    @Override
    public int findCountOfFeedbacks(Connection connection) throws SQLException {
        String sql = getSelectCountQuery();
        return findIntValueByQueryWithoutParameters(connection, sql);
    }

    @Override
    public int findCountOfFeedbacksByCustomerId(Connection connection, long customerId) throws SQLException {
        String sql = getSelectCountQuery();
        sql += " WHERE customer_id = ? AND is_hidden = false";
        return findIntValueByQueryWithParameters(connection, sql, customerId);
    }

    @Override
    public int findCountOfFeedbacksByMasterId(Connection connection, long masterId) throws SQLException {
        String sql = getSelectCountQuery();
        sql += " WHERE master_id = ? AND is_hidden = false";
        return findIntValueByQueryWithParameters(connection, sql, masterId);
    }

    @Override
    public int findCountOfFeedbacks(Connection connection, FeedbackFilterParameter filterParam, String filterValue
    ) throws SQLException {
        String sql = getSelectCountQuery();
        sql += " WHERE " + filterParam.getColumnName() + " = ? ";
        return findIntValueByQueryWithParameters(connection, sql, filterParam.getValue(filterValue));
    }

    @Override
    public List<Feedback> findAll(Connection connection, int offset, int amount, FeedbackSortingParameter sortingParam,
                                  SortingType sortingType, FeedbackFilterParameter filterParam, String filterValue
    ) throws SQLException, NotFoundException {
        String query = getSelectWithMasterAndCustomerFullNameQuery();
        query += " WHERE " + filterParam.getColumnName() + " = ? ";
        query += " ORDER BY " + sortingParam.getColumnName();
        query += " " + sortingType.getType();
        query += " LIMIT " + offset + ", " + amount;
        return findAllByQueryWithParameters(connection, query, filterParam.getValue(filterValue));
    }
}

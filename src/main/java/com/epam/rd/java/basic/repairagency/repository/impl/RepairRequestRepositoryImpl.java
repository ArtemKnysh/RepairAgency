package com.epam.rd.java.basic.repairagency.repository.impl;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Repository;
import com.epam.rd.java.basic.repairagency.repository.RepairRequestRepository;
import com.epam.rd.java.basic.repairagency.repository.UserRepository;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository(RepairRequestRepository.class)
public class RepairRequestRepositoryImpl extends AbstractRepository<RepairRequest> implements RepairRequestRepository {

    @Inject
    private UserRepository userRepository;

    private RepairRequestRepositoryImpl() {
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO repair_request (description, customer_id) " +
                "VALUES (?, ?);";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM repair_request";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE repair_request SET description = ?, customer_id = ?, cost = ? WHERE id =?;";
    }

    private String getUpdateRepairRequestCostQuery() {
        return "UPDATE repair_request SET cost = ? WHERE id =?;";
    }

    private String getUpdateRepairRequestDescriptionQuery() {
        return "UPDATE repair_request SET description = ? WHERE id =?;";
    }

    private String getUpdateRepairRequestMasterQuery() {
        return "UPDATE repair_request SET master_id = ? WHERE id =?;";
    }

    private String getUpdateRepairRequestStatusQuery() {
        return "UPDATE repair_request SET status_id = ? WHERE id =?;";
    }

    private String getRemoveMasterFromRepairRequestQuery() {
        return "UPDATE repair_request SET master_id = null WHERE id =?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM repair_request WHERE id = ?;";
    }

    @Override
    protected List<RepairRequest> parseResultSet(ResultSet rs) throws SQLException {
        List<RepairRequest> repairRequests = new ArrayList<>();
        while (rs.next()) {
            RepairRequest repairRequest = new RepairRequest();
            repairRequest.setId(rs.getLong("id"));
            repairRequest.setDescription(rs.getString("description"));
            repairRequest.setCustomerId(rs.getLong("customer_id"));
            repairRequest.setMasterId(rs.getLong("master_id"));
            repairRequest.setCost(rs.getDouble("cost"));
            repairRequest.setCreatedAt(rs.getTimestamp("create_time").toLocalDateTime());
            repairRequest.setStatus(RepairRequestStatus.getById(rs.getLong("status_id")));
            repairRequests.add(repairRequest);
        }
        return repairRequests;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, RepairRequest repairRequest) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, repairRequest.getDescription());
        statement.setLong(++paramIndex, repairRequest.getCustomerId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, RepairRequest repairRequest) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, repairRequest.getDescription());
        statement.setLong(++paramIndex, repairRequest.getCustomerId());
        statement.setDouble(++paramIndex, repairRequest.getCost());
        statement.setLong(++paramIndex, repairRequest.getId());
    }

    private void prepareStatementForUpdateCost(PreparedStatement statement, long repairRequestId, double cost) throws SQLException {
        int paramIndex = 0;
        statement.setDouble(++paramIndex, cost);
        statement.setLong(++paramIndex, repairRequestId);
    }

    private void prepareStatementForUpdateDescription(PreparedStatement statement, long repairRequestId, String description) throws SQLException {
        int paramIndex = 0;
        statement.setString(++paramIndex, description);
        statement.setLong(++paramIndex, repairRequestId);
    }

    private void prepareStatementForUpdateMasterId(PreparedStatement statement, long repairRequestId, long masterId) throws SQLException {
        int paramIndex = 0;
        statement.setLong(++paramIndex, masterId);
        statement.setLong(++paramIndex, repairRequestId);
    }

    private void prepareStatementForUpdateRepairRequestStatus(PreparedStatement statement,
                                                              long repairRequestId,
                                                              RepairRequestStatus status) throws SQLException {
        int paramIndex = 0;
        statement.setLong(++paramIndex, status.getId());
        statement.setLong(++paramIndex, repairRequestId);
    }

    private void prepareStatementForRemoveMaster(PreparedStatement statement, long repairRequestId) throws SQLException {
        int paramIndex = 0;
        statement.setLong(++paramIndex, repairRequestId);
    }

    @Override
    protected void findDefaultDependencies(Connection connection, RepairRequest repairRequest) throws SQLException, NotFoundException {
        repairRequest.setCustomer(userRepository.findById(connection, repairRequest.getCustomerId()));
        if (repairRequest.getMasterId() != 0) {
            repairRequest.setMaster(userRepository.findById(connection, repairRequest.getMasterId()));
        }
    }

    @Override
    public void setStatusForRepairRequest(Connection connection, long repairRequestId, RepairRequestStatus status) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getUpdateRepairRequestStatusQuery());
            prepareStatementForUpdateRepairRequestStatus(statement, repairRequestId, status);
            statement.executeUpdate();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public void setCostToRepairRequest(Connection connection, long repairRequestId, double cost) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getUpdateRepairRequestCostQuery());
            prepareStatementForUpdateCost(statement, repairRequestId, cost);
            statement.executeUpdate();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public void setDescriptionToRepairRequest(Connection connection, long repairRequestId, String description) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getUpdateRepairRequestDescriptionQuery());
            prepareStatementForUpdateDescription(statement, repairRequestId, description);
            statement.executeUpdate();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public void setMasterToRepairRequest(Connection connection, long repairRequestId, long masterId) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getUpdateRepairRequestMasterQuery());
            prepareStatementForUpdateMasterId(statement, repairRequestId, masterId);
            statement.executeUpdate();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public void removeMasterFromRepairRequest(Connection connection, long repairRequestId) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getRemoveMasterFromRepairRequestQuery());
            prepareStatementForRemoveMaster(statement, repairRequestId);
            statement.executeUpdate();
        } finally {
            DBUtil.close(statement);
        }
    }

    @Override
    public List<RepairRequest> findAllByCustomer(Connection connection, User customer) throws SQLException, NotFoundException {
        return findAllByCustomerId(connection, customer.getId());
    }

    @Override
    public List<RepairRequest> findAllByCustomerId(Connection connection, long customerId) throws SQLException, NotFoundException {
        List<RepairRequest> result;
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? ORDER BY create_time DESC;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, customerId);
            resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            for (RepairRequest repairRequest : result) {
                findDefaultDependencies(connection, repairRequest);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    @Override
    public List<RepairRequest> findAllByMasterId(Connection connection, long masterId) throws SQLException, NotFoundException {
        List<RepairRequest> result;
        String sql = getSelectQuery();
        sql += " WHERE master_id = ? ORDER BY create_time DESC;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, masterId);
            resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            for (RepairRequest repairRequest : result) {
                findDefaultDependencies(connection, repairRequest);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }

    @Override
    public List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusMoreThenStatusId(Connection connection, long customerId, long masterId, long statusId) throws SQLException, NotFoundException {
        List<RepairRequest> result;
        String sql = getSelectQuery();
        sql += " WHERE customer_id = ? AND master_id = ? AND status_id > ? ORDER BY create_time DESC;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, customerId);
            statement.setLong(2, masterId);
            statement.setLong(3, statusId);
            resultSet = statement.executeQuery();
            result = parseResultSet(resultSet);
            for (RepairRequest repairRequest : result) {
                findDefaultDependencies(connection, repairRequest);
            }
            return result;
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
        }
    }
}

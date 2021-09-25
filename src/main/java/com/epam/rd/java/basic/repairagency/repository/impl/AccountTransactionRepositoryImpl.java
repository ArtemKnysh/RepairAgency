package com.epam.rd.java.basic.repairagency.repository.impl;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.factory.anotation.Repository;
import com.epam.rd.java.basic.repairagency.repository.AccountTransactionRepository;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository(AccountTransactionRepository.class)
public class AccountTransactionRepositoryImpl extends AbstractRepository<AccountTransaction> implements AccountTransactionRepository {

    private AccountTransactionRepositoryImpl() {
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM account_transaction";
    }

    protected String getSelectAmountSumQuery() {
        return "SELECT SUM(amount) FROM account_transaction";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO account_transaction " +
                "(user_id, amount) VALUES (?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE account_transaction SET user_id = ?, amount = ? WHERE id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE account_transaction WHERE id = ?;";
    }

    @Override
    protected List<AccountTransaction> parseResultSet(ResultSet rs) throws SQLException {
        List<AccountTransaction> result = new ArrayList<>();
        while (rs.next()) {
            AccountTransaction accountTransaction = new AccountTransaction();
            accountTransaction.setId(rs.getLong("id"));
            accountTransaction.setUserId(rs.getLong("user_id"));
            accountTransaction.setAmount(rs.getDouble("amount"));
            accountTransaction.setCreatedAt(rs.getTimestamp("create_time").toLocalDateTime());
            result.add(accountTransaction);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, AccountTransaction accountTransaction) throws SQLException {
        int paramIndex = 0;
        statement.setLong(++paramIndex, accountTransaction.getUserId());
        statement.setDouble(++paramIndex, accountTransaction.getAmount());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, AccountTransaction accountTransaction) throws SQLException {
        int paramIndex = 0;
        statement.setLong(++paramIndex, accountTransaction.getUserId());
        statement.setDouble(++paramIndex, accountTransaction.getAmount());
        statement.setLong(++paramIndex, accountTransaction.getId());
    }

    @Override
    public double findSumOfAmountByUserId(Connection connection, long userId) throws SQLException {
        String sql = getSelectAmountSumQuery();
        sql += " WHERE user_id = ?;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
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

    @Override
    public List<AccountTransaction> findAllByUserId(Connection connection, long userId) throws SQLException, NotFoundException {
        String sql = getSelectQuery();
        sql += " WHERE user_id = ? ORDER BY create_time DESC;";
        return findAllByQueryWithOneParameter(connection, sql, userId);
    }

    @Override
    public void insertAll(Connection connection, AccountTransaction... accountTransactions) throws SQLException {
        String sql = getInsertQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            for (AccountTransaction accountTransaction : accountTransactions) {
                prepareStatementForInsert(statement, accountTransaction);
                statement.addBatch();
            }
            statement.executeBatch();
        } finally {
            DBUtil.close(statement);
        }

    }
}

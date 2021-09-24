package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountTransactionRepository extends GenericRepository<AccountTransaction> {

    double findSumOfAmountByUserId(Connection connection, long userId) throws SQLException;

    List<AccountTransaction> findAllByUserId(Connection connection, long userId) throws SQLException;

    void insertAll(Connection connection, AccountTransaction... accountTransactions) throws SQLException;
}

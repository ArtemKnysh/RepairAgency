package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;
import com.epam.rd.java.basic.repairagency.entity.sorting.AccountTransactionSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountTransactionRepository extends GenericRepository<AccountTransaction> {

    void insertAll(Connection connection, AccountTransaction... accountTransactions) throws SQLException;

    double findSumOfAmountByUserId(Connection connection, long userId) throws SQLException;

    int findCountOfTransactionsByUserId(Connection connection, long userId) throws SQLException;

    List<AccountTransaction> findAllByUserId(Connection connection, long userId) throws SQLException, NotFoundException;

    List<AccountTransaction> findAllByUserId(Connection connection, long userId, int offset, int amount,
                                             AccountTransactionSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException;
}

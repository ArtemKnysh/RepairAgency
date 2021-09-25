package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;
import com.epam.rd.java.basic.repairagency.entity.sorting.AccountTransactionSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountTransactionService extends GenericService<AccountTransaction> {

    double findSumOfAmountByUserId(long userId) throws DBException;

    List<AccountTransaction> findAllByUserId(long userId) throws DBException, NotFoundException;

    void payForRepairRequest(long userId, long repairRequestId) throws NotFoundException, DBException;

    void transferBetweenAccounts(long fromAccountOfUserId, long toAccountOfUserId, double amount) throws DBException;

    int findCountOfTransactionsByUserId(long userId) throws DBException;

    List<AccountTransaction> findAllByUserId(long userId, int offset, int amount,
                                             AccountTransactionSortingParameter accountTransactionSortingParameter,
                                             SortingType sortingType) throws DBException, NotFoundException;
}

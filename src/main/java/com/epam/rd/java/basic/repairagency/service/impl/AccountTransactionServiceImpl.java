package com.epam.rd.java.basic.repairagency.service.impl;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;
import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Service;
import com.epam.rd.java.basic.repairagency.repository.AccountTransactionRepository;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.repository.RepairRequestRepository;
import com.epam.rd.java.basic.repairagency.service.AccountTransactionService;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service(AccountTransactionService.class)
public class AccountTransactionServiceImpl extends AbstractService<AccountTransaction> implements AccountTransactionService {

    @Inject
    private AccountTransactionRepository repository;
    @Inject
    private RepairRequestRepository repairRequestRepository;

    private AccountTransactionServiceImpl() {
    }

    @Override
    protected GenericRepository<AccountTransaction> getRepository() {
        return repository;
    }

    @Override
    public double findSumOfAmountByUserId(long userId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findSumOfAmountByUserId(connection, userId);
        } catch (SQLException e) {
            throw new DBException("Can't find sum of account transaction amounts with userId '" + userId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<AccountTransaction> findAllByUserId(long userId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByUserId(connection, userId);
        } catch (SQLException e) {
            throw new DBException("Can't find account transaction with userId '" + userId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void payForRepairRequest(long userId, long repairRequestId) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            RepairRequest repairRequest = repairRequestRepository.findById(connection, repairRequestId);
            AccountTransaction accountTransaction = new AccountTransaction();
            accountTransaction.setUserId(userId);
            accountTransaction.setAmount(-repairRequest.getCost());
            repository.insert(connection, accountTransaction);
            repairRequestRepository.setStatusForRepairRequest(connection, repairRequestId, RepairRequestStatus.PAID);
            connection.commit();
        } catch (SQLException e) {
            DBUtil.rollbackTransaction(connection);
            throw new DBException("Can't pay for repair request with id '" + repairRequestId + "' " +
                    "by user with id '" + userId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void transferBetweenAccounts(long fromAccountUserId, long toAccountUserId, double amount) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            double fromAccountBalance = repository.findSumOfAmountByUserId(connection, fromAccountUserId);
            if (fromAccountBalance < amount) {
                throw new IllegalStateException("Can't do transfer from account with id '" + fromAccountUserId + "' " +
                        "to account with id '" + toAccountUserId + "'. From account balance (" + fromAccountBalance + ") is lower then " +
                        "amount (" + amount + ")");
            }
            AccountTransaction transferFromAccount = new AccountTransaction();
            transferFromAccount.setUserId(fromAccountUserId);
            transferFromAccount.setAmount(-amount);
            AccountTransaction transferToAccount = new AccountTransaction();
            transferToAccount.setUserId(toAccountUserId);
            transferToAccount.setAmount(amount);
            repository.insertAll(connection, transferFromAccount, transferToAccount);
            connection.commit();
        } catch (SQLException e) {
            DBUtil.rollbackTransaction(connection);
            throw new DBException("Can't do transfer from account with id '" + fromAccountUserId + "' " +
                    "to account with id '" + toAccountUserId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }
}

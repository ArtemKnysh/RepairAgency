package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.AccountTransaction;
import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.sorting.AccountTransactionSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.base.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountTransactionServiceTest extends ServiceTest {

    protected static AccountTransactionService accountTransactionService;
    protected static UserService userService;
    private static RepairRequestService repairRequestService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ServiceTest.beforeClass();
        accountTransactionService = (AccountTransactionService) serviceFactory.getService(AccountTransactionService.class);
        userService = (UserService) serviceFactory.getService(UserService.class);
        repairRequestService = (RepairRequestService) serviceFactory.getService(RepairRequestService.class);
    }

    public static AccountTransaction createAccountTransaction(long userId, double amount) {
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAmount(amount);
        accountTransaction.setUserId(userId);
        return accountTransaction;
    }

    public static boolean isAccountTransactionEquals(AccountTransaction firstAccountTransaction, AccountTransaction secondAccountTransaction) {
        return firstAccountTransaction.getId() == secondAccountTransaction.getId() &&
                firstAccountTransaction.getUserId() == secondAccountTransaction.getUserId() &&
                firstAccountTransaction.getAmount() == secondAccountTransaction.getAmount();
    }

    @Test
    public void testInsertAccountTransaction() {
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            user = userService.findById(user.getId());
            AccountTransaction accountTransaction = createAccountTransaction(user.getId(), 10.0);
            accountTransactionService.insert(accountTransaction);
            AccountTransaction accountTransactionFromDB = accountTransactionService.findById(accountTransaction.getId());
            assertTrue(isAccountTransactionEquals(accountTransaction, accountTransactionFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateAccountTransaction() {
        try {
            User user1 = UserServiceTest.createUser(1);
            userService.insert(user1);
            user1 = userService.findById(user1.getId());
            User user2 = UserServiceTest.createUser(2);
            userService.insert(user2);
            user2 = userService.findById(user2.getId());
            AccountTransaction accountTransaction = createAccountTransaction(user1.getId(), 10.0);
            accountTransactionService.insert(accountTransaction);
            accountTransaction = accountTransactionService.findById(accountTransaction.getId());
            accountTransaction.setAmount(20.0);
            accountTransaction.setUserId(user2.getId());
            accountTransactionService.update(accountTransaction);
            AccountTransaction accountTransactionFromDB = accountTransactionService.findById(accountTransaction.getId());
            assertTrue(isAccountTransactionEquals(accountTransaction, accountTransactionFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteAccountTransaction() {
        AccountTransaction accountTransaction = null;
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            accountTransaction = createAccountTransaction(user.getId(), 10.0);
            accountTransactionService.insert(accountTransaction);
            accountTransactionService.delete(accountTransaction);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            accountTransactionService.findById(accountTransaction.getId());
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindById() {
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            AccountTransaction accountTransaction = createAccountTransaction(user.getId(), 10.0);
            accountTransactionService.insert(accountTransaction);
            AccountTransaction accountTransactionFromDB = accountTransactionService.findById(accountTransaction.getId());
            assertTrue(isAccountTransactionEquals(accountTransaction, accountTransactionFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testFindAll() {
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            user = userService.findById(user.getId());
            AccountTransaction accountTransaction1 = createAccountTransaction(user.getId(), 10.0);
            accountTransactionService.insert(accountTransaction1);
            AccountTransaction accountTransaction2 = createAccountTransaction(user.getId(), 20.0);
            accountTransactionService.insert(accountTransaction2);
            AccountTransaction accountTransaction3 = createAccountTransaction(user.getId(), 30.0);
            accountTransactionService.insert(accountTransaction3);
            List<AccountTransaction> feedbacksFromDB = accountTransactionService.findAll();
            assertEquals(3, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(accountTransaction1));
            assertTrue(feedbacksFromDB.contains(accountTransaction2));
            assertTrue(feedbacksFromDB.contains(accountTransaction3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindSumOfAmountByUserId() {
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            user = userService.findById(user.getId());
            AccountTransaction accountTransaction1 = createAccountTransaction(user.getId(), 10.0);
            accountTransactionService.insert(accountTransaction1);
            AccountTransaction accountTransaction2 = createAccountTransaction(user.getId(), 20.0);
            accountTransactionService.insert(accountTransaction2);
            AccountTransaction accountTransaction3 = createAccountTransaction(user.getId(), 30.0);
            accountTransactionService.insert(accountTransaction3);
            double sumOfAmountByUserId = accountTransactionService.findSumOfAmountByUserId(user.getId());
            assertEquals(60.0, sumOfAmountByUserId, 0.001);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByUserId() {
        try {
            User user1 = UserServiceTest.createUser(1);
            userService.insert(user1);
            user1 = userService.findById(user1.getId());
            User user2 = UserServiceTest.createUser(2);
            userService.insert(user2);
            user2 = userService.findById(user2.getId());
            AccountTransaction accountTransaction1 = createAccountTransaction(user1.getId(), 10.0);
            accountTransactionService.insert(accountTransaction1);
            AccountTransaction accountTransaction2 = createAccountTransaction(user1.getId(), 20.0);
            accountTransactionService.insert(accountTransaction2);
            AccountTransaction accountTransaction3 = createAccountTransaction(user2.getId(), 30.0);
            accountTransactionService.insert(accountTransaction3);
            List<AccountTransaction> feedbacksFromDB = accountTransactionService.findAllByUserId(user1.getId());
            assertEquals(2, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(accountTransaction1));
            assertTrue(feedbacksFromDB.contains(accountTransaction2));
            assertFalse(feedbacksFromDB.contains(accountTransaction3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAllByUserId() {
        try {
            User user1 = UserServiceTest.createUser(1);
            userService.insert(user1);
            user1 = userService.findById(user1.getId());
            User user2 = UserServiceTest.createUser(2);
            userService.insert(user2);
            user2 = userService.findById(user2.getId());
            AccountTransaction accountTransaction1 = createAccountTransaction(user1.getId(), 10.0);
            accountTransactionService.insert(accountTransaction1);
            AccountTransaction accountTransaction2 = createAccountTransaction(user1.getId(), 20.0);
            accountTransactionService.insert(accountTransaction2);
            AccountTransaction accountTransaction3 = createAccountTransaction(user2.getId(), 30.0);
            accountTransactionService.insert(accountTransaction3);
            List<AccountTransaction> feedbacksFromDB = accountTransactionService.findAllByUserId(user1.getId(), 0, 1, AccountTransactionSortingParameter.AMOUNT, SortingType.ASC);
            assertEquals(1, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(accountTransaction1));
            assertFalse(feedbacksFromDB.contains(accountTransaction2));
            assertFalse(feedbacksFromDB.contains(accountTransaction3));
            feedbacksFromDB = accountTransactionService.findAllByUserId(user1.getId(), 1, 1, AccountTransactionSortingParameter.AMOUNT, SortingType.ASC);
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(accountTransaction1));
            assertTrue(feedbacksFromDB.contains(accountTransaction2));
            assertFalse(feedbacksFromDB.contains(accountTransaction3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPayForRepairRequest() {
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            AccountTransaction accountTransaction = createAccountTransaction(user.getId(), 100.0);
            accountTransactionService.insert(accountTransaction);
            RepairRequest repairRequest = RepairRequestServiceTest.createRepairRequest(1, user.getId());
            repairRequestService.insert(repairRequest);
            repairRequestService.setCostToRepairRequest(repairRequest.getId(), 50.0);
            repairRequestService.setStatusToRepairRequest(repairRequest.getId(), RepairRequestStatus.WAIT_FOR_PAYMENT);
            accountTransactionService.payForRepairRequest(user.getId(), repairRequest.getId());
            double userBalance = accountTransactionService.findSumOfAmountByUserId(user.getId());
            assertEquals(50.0, userBalance, 0.001);
            int countOfTransactionsByUser = accountTransactionService.findCountOfTransactionsByUserId(user.getId());
            assertEquals(2, countOfTransactionsByUser);
            RepairRequest repairRequestFromDB = repairRequestService.findById(repairRequest.getId());
            assertEquals(RepairRequestStatus.PAID, repairRequestFromDB.getStatus());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTransferBetweenAccounts() {
        try {
            User fromUser = UserServiceTest.createUser(1);
            userService.insert(fromUser);
            AccountTransaction accountTransaction = createAccountTransaction(fromUser.getId(), 100.0);
            accountTransactionService.insert(accountTransaction);
            User toUser = UserServiceTest.createUser(2);
            userService.insert(toUser);
            accountTransactionService.transferBetweenAccounts(fromUser.getId(), toUser.getId(), 30.0);
            double fromUserBalance = accountTransactionService.findSumOfAmountByUserId(fromUser.getId());
            assertEquals(70.0, fromUserBalance, 0.001);
            double toUserBalance = accountTransactionService.findSumOfAmountByUserId(toUser.getId());
            assertEquals(30.0, toUserBalance, 0.001);
            int countOfTransactionsByFromUser = accountTransactionService.findCountOfTransactionsByUserId(fromUser.getId());
            assertEquals(2, countOfTransactionsByFromUser);
            int countOfTransactionsByToUser = accountTransactionService.findCountOfTransactionsByUserId(toUser.getId());
            assertEquals(1, countOfTransactionsByToUser);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTransferBetweenAccountsWhenFormAccountBalanceIsLowerThenAmount() {
        try {
            User fromUser = UserServiceTest.createUser(1);
            userService.insert(fromUser);
            AccountTransaction accountTransaction = createAccountTransaction(fromUser.getId(), 10.0);
            accountTransactionService.insert(accountTransaction);
            User toUser = UserServiceTest.createUser(2);
            userService.insert(toUser);
            try {
                accountTransactionService.transferBetweenAccounts(fromUser.getId(), toUser.getId(), 30.0);
                fail();
            } catch (IllegalStateException ignored) {
            }
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfTransactionsByUserId() {
        try {
            User user = UserServiceTest.createUser(1);
            userService.insert(user);
            user = userService.findById(user.getId());
            accountTransactionService.insert(createAccountTransaction(user.getId(), 10.0));
            accountTransactionService.insert(createAccountTransaction(user.getId(), 10.0));
            accountTransactionService.insert(createAccountTransaction(user.getId(), 10.0));
            accountTransactionService.insert(createAccountTransaction(user.getId(), 10.0));
            int countOfTransactionsByUser = accountTransactionService.findCountOfTransactionsByUserId(user.getId());
            assertEquals(4, countOfTransactionsByUser);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }

    }
}

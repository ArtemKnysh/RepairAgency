package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.filtering.FeedbackFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.base.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FeedbackServiceTest extends ServiceTest {

    protected static FeedbackService feedbackService;
    protected static UserService userService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ServiceTest.beforeClass();
        feedbackService = (FeedbackService) serviceFactory.getService(FeedbackService.class);
        userService = (UserService) serviceFactory.getService(UserService.class);
    }

    public static Feedback createFeedback(int number, long customerId, long masterId) {
        Feedback feedback = new Feedback();
        feedback.setText("Text " + number);
        feedback.setCustomerId(customerId);
        feedback.setMasterId(masterId);
        return feedback;
    }

    public static boolean isFeedbacksEquals(Feedback firstFeedback, Feedback secondFeedback) {
        return firstFeedback.getId() == secondFeedback.getId() &&
                firstFeedback.getText().equals(secondFeedback.getText()) &&
                firstFeedback.getCustomerId() == secondFeedback.getCustomerId() &&
                firstFeedback.getMasterId() == secondFeedback.getMasterId();
    }

    @Test
    public void testInsertFeedback() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            Feedback feedbackFromDB = feedbackService.findById(feedback.getId());
            assertTrue(isFeedbacksEquals(feedback, feedbackFromDB));
            assertTrue(UserServiceTest.isUsersEquals(customer, feedbackFromDB.getCustomer()));
            assertTrue(UserServiceTest.isUsersEquals(master, feedbackFromDB.getMaster()));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateFeedback() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            User master1 = UserServiceTest.createUser(2);
            userService.insert(master1);
            master1 = userService.findById(master1.getId());
            User customer2 = UserServiceTest.createUser(3);
            userService.insert(customer2);
            customer2 = userService.findById(customer2.getId());
            User master2 = UserServiceTest.createUser(4);
            userService.insert(master2);
            master2 = userService.findById(master2.getId());
            Feedback feedback = createFeedback(1, customer1.getId(), master1.getId());
            feedbackService.insert(feedback);
            feedback = feedbackService.findById(feedback.getId());
            feedback.setText("Text 2");
            feedback.setCustomer(customer2);
            feedback.setMaster(master2);
            feedbackService.update(feedback);
            Feedback updatedFeedback = feedbackService.findById(feedback.getId());
            assertTrue(isFeedbacksEquals(feedback, updatedFeedback));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteFeedback() {
        Feedback feedback = null;
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            feedbackService.delete(feedback);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            feedbackService.findById(feedback.getId());
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindById() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            Feedback feedbackFromDB = feedbackService.findById(feedback.getId());
            assertTrue(isFeedbacksEquals(feedback, feedbackFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByIdWithNotExistedId() {
        try {
            feedbackService.findById(1);
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindAll() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAll();
            assertEquals(3, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertTrue(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByCustomerIdAndMasterId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            Feedback feedbackFromDB = feedbackService.findByCustomerIdAndMasterIdExcludeHidden(customer.getId(), master.getId());
            assertTrue(isFeedbacksEquals(feedback, feedbackFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByCustomerIdAndMasterIdWhenFeedbackIsHidden() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            feedbackService.hide(feedback.getId());
            try {
                feedbackService.findByCustomerIdAndMasterIdExcludeHidden(customer.getId(), master.getId());
                fail();
            } catch (NotFoundException ignored) {

            }
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByCustomerIdAndMasterIdWithNotExistedInputData() {
        User customer = UserServiceTest.createUser(1);
        User master = UserServiceTest.createUser(2);
        try {
            userService.insert(customer);
            userService.insert(master);
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            feedbackService.findByCustomerIdAndMasterIdExcludeHidden(customer.getId() + 1, master.getId());
            fail();
        } catch (NotFoundException ignored) {

        } catch (DBException e) {
            fail(e.getMessage());
        }
        try {
            feedbackService.findByCustomerIdAndMasterIdExcludeHidden(customer.getId(), master.getId() + 1);
            fail();
        } catch (NotFoundException ignored) {

        } catch (DBException e) {
            fail(e.getMessage());
        }
        try {
            feedbackService.findByCustomerIdAndMasterIdExcludeHidden(customer.getId() + 1, master.getId() + 1);
            fail();
        } catch (NotFoundException ignored) {

        } catch (DBException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByMasterIdExceptCustomerId() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            customer2 = userService.findById(customer2.getId());
            User master = UserServiceTest.createUser(3);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer1.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer2.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer2.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAllByMasterIdExceptCustomerIdExcludeHidden(master.getId(), customer1.getId());
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByCustomerId() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            customer2 = userService.findById(customer2.getId());
            User master = UserServiceTest.createUser(3);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer1.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer2.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer1.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAllByCustomerIdExcludeHidden(customer1.getId());
            assertEquals(1, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(feedback1));
            assertFalse(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByCustomerIdIncludeHidden() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            customer2 = userService.findById(customer2.getId());
            User master = UserServiceTest.createUser(3);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer1.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer2.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer1.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAllByCustomerId(customer1.getId());
            assertEquals(2, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(feedback1));
            assertFalse(feedbacksFromDB.contains(feedback2));
            assertTrue(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByMasterId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master1 = UserServiceTest.createUser(2);
            userService.insert(master1);
            master1 = userService.findById(master1.getId());
            User master2 = UserServiceTest.createUser(3);
            userService.insert(master2);
            master2 = userService.findById(master2.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master2.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master1.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master1.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAllByMasterIdExcludeHidden(master1.getId());
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByMasterIdIncludeHidden() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master1 = UserServiceTest.createUser(2);
            userService.insert(master1);
            master1 = userService.findById(master1.getId());
            User master2 = UserServiceTest.createUser(3);
            userService.insert(master2);
            master2 = userService.findById(master2.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master2.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master1.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master1.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAllByMasterId(master1.getId());
            assertEquals(2, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertTrue(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testHideFeedback() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            feedbackService.hide(feedback.getId());
            Feedback feedbackFromDB = feedbackService.findById(feedback.getId());
            assertTrue(feedbackFromDB.getIsHidden());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testShowFeedback() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback);
            feedbackService.show(feedback.getId());
            Feedback feedbackFromDB = feedbackService.findById(feedback.getId());
            assertFalse(feedbackFromDB.getIsHidden());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findCountOfFeedbacks() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            int countOfFeedbacks = feedbackService.findCountOfFeedbacks();
            assertEquals(3, countOfFeedbacks);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfFeedbacksByCustomerId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            int countOfFeedbacks = feedbackService.findCountOfFeedbacksByCustomerIdExcludeHidden(customer.getId());
            assertEquals(2, countOfFeedbacks);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfFeedbacksByMasterId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            int countOfFeedbacks = feedbackService.findCountOfFeedbacksByMasterIdExcludeHidden(master.getId());
            assertEquals(2, countOfFeedbacks);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAllByCustomerId() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            customer2 = userService.findById(customer2.getId());
            User master = UserServiceTest.createUser(3);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer1.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer2.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer1.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            Feedback feedback4 = createFeedback(4, customer1.getId(), master.getId());
            feedbackService.insert(feedback4);
            List<Feedback> feedbacksFromDB = feedbackService.findAllByCustomerIdExcludeHidden(customer1.getId(), 0, 1, FeedbackSortingParameter.TEXT, SortingType.ASC);
            assertEquals(1, feedbacksFromDB.size());
            assertTrue(feedbacksFromDB.contains(feedback1));
            assertFalse(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
            assertFalse(feedbacksFromDB.contains(feedback4));
            feedbacksFromDB = feedbackService.findAllByCustomerIdExcludeHidden(customer1.getId(), 1, 1, FeedbackSortingParameter.TEXT, SortingType.ASC);
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertFalse(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
            assertTrue(feedbacksFromDB.contains(feedback4));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAllByMasterId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master1 = UserServiceTest.createUser(2);
            userService.insert(master1);
            master1 = userService.findById(master1.getId());
            User master2 = UserServiceTest.createUser(3);
            userService.insert(master2);
            master2 = userService.findById(master2.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master2.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master1.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master1.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            Feedback feedback4 = createFeedback(4, customer.getId(), master1.getId());
            feedbackService.insert(feedback4);
            List<Feedback> feedbacksFromDB = feedbackService.findAllByMasterIdExcludeHidden(master1.getId(), 0, 1, FeedbackSortingParameter.TEXT, SortingType.DESC);
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertFalse(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
            assertTrue(feedbacksFromDB.contains(feedback4));
            feedbacksFromDB = feedbackService.findAllByMasterIdExcludeHidden(master1.getId(), 1, 1, FeedbackSortingParameter.TEXT, SortingType.DESC);
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
            assertFalse(feedbacksFromDB.contains(feedback4));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFilteringInFindCountOfFeedbacks() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            feedbackService.hide(feedback2.getId());
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            int countOfFeedbacks = feedbackService.findCountOfFeedbacks(FeedbackFilterParameter.IS_HIDDEN, "true");
            assertEquals(2, countOfFeedbacks);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationAndFilteringInFindAll() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            feedbackService.hide(feedback1.getId());
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAll(0, 1, FeedbackSortingParameter.TEXT, SortingType.DESC, FeedbackFilterParameter.IS_HIDDEN, "true");
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertFalse(feedbacksFromDB.contains(feedback2));
            assertTrue(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAll() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            Feedback feedback1 = createFeedback(1, customer.getId(), master.getId());
            feedbackService.insert(feedback1);
            feedbackService.hide(feedback1.getId());
            Feedback feedback2 = createFeedback(2, customer.getId(), master.getId());
            feedbackService.insert(feedback2);
            Feedback feedback3 = createFeedback(3, customer.getId(), master.getId());
            feedbackService.insert(feedback3);
            feedbackService.hide(feedback3.getId());
            List<Feedback> feedbacksFromDB = feedbackService.findAll(1, 1, FeedbackSortingParameter.TEXT, SortingType.DESC);
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
            feedbacksFromDB = feedbackService.findAll(0, 1, FeedbackSortingParameter.IS_HIDDEN, SortingType.ASC);
            assertEquals(1, feedbacksFromDB.size());
            assertFalse(feedbacksFromDB.contains(feedback1));
            assertTrue(feedbacksFromDB.contains(feedback2));
            assertFalse(feedbacksFromDB.contains(feedback3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }
}

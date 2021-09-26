package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.filtering.RepairRequestFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.RepairRequestSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.base.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RepairRequestServiceTest extends ServiceTest {

    protected static RepairRequestService repairRequestService;
    protected static UserService userService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ServiceTest.beforeClass();
        repairRequestService = (RepairRequestService) serviceFactory.getService(RepairRequestService.class);
        userService = (UserService) serviceFactory.getService(UserService.class);
    }

    public static RepairRequest createRepairRequest(int number, long customerId) {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setDescription("Description " + number);
        repairRequest.setCustomerId(customerId);
        return repairRequest;
    }

    private boolean isRepairRequestsEquals(RepairRequest firstRepairRequest, RepairRequest secondRepairRequest) {
        return firstRepairRequest.getId() == secondRepairRequest.getId() &&
                firstRepairRequest.getDescription().equals(secondRepairRequest.getDescription()) &&
                firstRepairRequest.getCustomerId() == secondRepairRequest.getCustomerId() &&
                firstRepairRequest.getCost() == secondRepairRequest.getCost() &&
                firstRepairRequest.getStatus() == secondRepairRequest.getStatus() &&
                firstRepairRequest.getMasterId() == secondRepairRequest.getMasterId();
    }

    @Test
    public void testInsertRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequest = repairRequestService.findById(repairRequest.getId());
            assertNotEquals(0, repairRequest.getId());
            assertEquals(RepairRequestStatus.CREATED, repairRequest.getStatus());
            assertEquals(customer.getId(), repairRequest.getCustomerId());
            assertTrue(UserServiceTest.isUsersEquals(customer, repairRequest.getCustomer()));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequest = repairRequestService.findById(repairRequest.getId());
            repairRequest.setDescription("Description 2");
            repairRequest.setCost(1111);
            repairRequestService.update(repairRequest);
            RepairRequest updatedRepairRequest = repairRequestService.findById(repairRequest.getId());
            assertTrue(isRepairRequestsEquals(repairRequest, updatedRepairRequest));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteRepairRequest() {
        RepairRequest repairRequest = null;
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequestService.delete(repairRequest);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            repairRequestService.findById(repairRequest.getId());
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
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequest.setStatus(RepairRequestStatus.CREATED);
            RepairRequest repairRequestFromDB = repairRequestService.findById(repairRequest.getId());
            assertTrue(isRepairRequestsEquals(repairRequest, repairRequestFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testFindByIdWithNotExistedId() {
        try {
            repairRequestService.findById(1);
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
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAll();
            assertEquals(2, repairRequestsFromDB.size());
            assertTrue(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testFindAllWithEmptyDB() {
        try {
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAll();
            assertTrue(repairRequestsFromDB.isEmpty());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCancelRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequestService.cancelRepairRequest(repairRequest.getId());
            RepairRequest cancelledRepairRequest = repairRequestService.findById(repairRequest.getId());
            assertEquals(RepairRequestStatus.CANCELLED, cancelledRepairRequest.getStatus());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCancelRepairRequestWithCancelledStatus() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequestService.cancelRepairRequest(repairRequest.getId());
            repairRequestService.cancelRepairRequest(repairRequest.getId());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testCancelRepairRequestWithRepairRequestThatCantBeCancelled() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequestService.setStatusToRepairRequest(repairRequest.getId(), RepairRequestStatus.PAID);
            RepairRequest repairRequestThatCantBeCancelled = repairRequestService.findById(repairRequest.getId());
            assertFalse(repairRequestThatCantBeCancelled.isCanBeCancelled());
            try {
                repairRequestService.cancelRepairRequest(repairRequestThatCantBeCancelled.getId());
                fail();
            } catch (DBException ignored) {
            }
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetStatusToRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            repairRequestService.setStatusToRepairRequest(repairRequest.getId(), RepairRequestStatus.PAID);
            RepairRequest repairRequestFromDB = repairRequestService.findById(repairRequest.getId());
            assertEquals(RepairRequestStatus.PAID, repairRequestFromDB.getStatus());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetCostToRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            double cost = 20;
            repairRequestService.setCostToRepairRequest(repairRequest.getId(), cost);
            RepairRequest repairRequestFromDB = repairRequestService.findById(repairRequest.getId());
            assertEquals(cost, repairRequestFromDB.getCost(), 0.001);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetDescriptionToRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            String description = "Description 2";
            repairRequestService.setDescriptionToRepairRequest(repairRequest.getId(), description);
            RepairRequest repairRequestFromDB = repairRequestService.findById(repairRequest.getId());
            assertEquals(description, repairRequestFromDB.getDescription());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetMasterToRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            repairRequestService.setMasterToRepairRequest(repairRequest.getId(), master.getId());
            RepairRequest repairRequestFromDB = repairRequestService.findById(repairRequest.getId());
            assertTrue(UserServiceTest.isUsersEquals(master, repairRequestFromDB.getMaster()));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveMasterFromRepairRequest() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest);
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            master = userService.findById(master.getId());
            repairRequestService.setMasterToRepairRequest(repairRequest.getId(), master.getId());
            repairRequestService.removeMasterFromRepairRequest(repairRequest.getId());
            RepairRequest repairRequestFromBD = repairRequestService.findById(repairRequest.getId());
            assertEquals(0, repairRequestFromBD.getMasterId());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByCustomer() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByCustomer(customer);
            assertEquals(2, repairRequestsFromDB.size());
            assertTrue(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
            repairRequestsFromDB = repairRequestService.findAllByCustomerId(-1);
            assertTrue(repairRequestsFromDB.isEmpty());
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
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            userService.setRoleToUser(master, UserRole.MASTER);
            master = userService.findById(master.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setMasterToRepairRequest(repairRequest1.getId(), master.getId());
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequestService.setMasterToRepairRequest(repairRequest2.getId(), master.getId());
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByMasterId(master.getId());
            assertEquals(2, repairRequestsFromDB.size());
            assertTrue(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
            repairRequestsFromDB = repairRequestService.findAllByMasterId(customer.getId());
            assertTrue(repairRequestsFromDB.isEmpty());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByCustomerIdAndMasterIdAndStatusMoreThenPaid() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            userService.setRoleToUser(master, UserRole.MASTER);
            master = userService.findById(master.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setMasterToRepairRequest(repairRequest1.getId(), master.getId());
            repairRequestService.setStatusToRepairRequest(repairRequest1.getId(), RepairRequestStatus.PAID);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequestService.setMasterToRepairRequest(repairRequest2.getId(), master.getId());
            repairRequestService.setStatusToRepairRequest(repairRequest2.getId(), RepairRequestStatus.IN_WORK);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByCustomerIdAndMasterIdAndStatusMoreThenPaid(customer.getId(), master.getId());
            System.out.println("repairRequestsFromDB = " + repairRequestsFromDB);
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByCustomerIdAndMasterIdAndStatusCompleted() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            userService.setRoleToUser(master, UserRole.MASTER);
            master = userService.findById(master.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setMasterToRepairRequest(repairRequest1.getId(), master.getId());
            repairRequestService.setStatusToRepairRequest(repairRequest1.getId(), RepairRequestStatus.GIVEN_TO_MASTER);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequestService.setMasterToRepairRequest(repairRequest2.getId(), master.getId());
            repairRequestService.setStatusToRepairRequest(repairRequest2.getId(), RepairRequestStatus.COMPLETED);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByCustomerIdAndMasterIdAndStatusCompleted(customer.getId(), master.getId());
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfRepairRequests() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            int countOfRepairRequests = repairRequestService.findCountOfRepairRequests();
            assertEquals(2, countOfRepairRequests);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfRepairRequestsByCustomerId() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            RepairRequest repairRequest1 = createRepairRequest(1, customer1.getId());
            repairRequestService.insert(repairRequest1);
            RepairRequest repairRequest2 = createRepairRequest(2, customer2.getId());
            repairRequestService.insert(repairRequest2);
            int countOfRepairRequests = repairRequestService.findCountOfRepairRequestsByCustomerId(customer1.getId());
            assertEquals(1, countOfRepairRequests);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfRepairRequestsByMasterId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            userService.setRoleToUser(master, UserRole.MASTER);
            master = userService.findById(master.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setMasterToRepairRequest(repairRequest1.getId(), master.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            int countOfRepairRequests = repairRequestService.findCountOfRepairRequestsByMasterId(master.getId());
            assertEquals(1, countOfRepairRequests);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAll() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer1.getId());
            repairRequestService.insert(repairRequest1);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            customer2 = userService.findById(customer2.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer2.getId());
            repairRequestService.insert(repairRequest2);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAll(0, 1, RepairRequestSortingParameter.CUSTOMER_FULL_NAME, SortingType.DESC);
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
            repairRequestsFromDB = repairRequestService.findAll(1, 1, RepairRequestSortingParameter.CUSTOMER_FULL_NAME, SortingType.DESC);
            assertEquals(1, repairRequestsFromDB.size());
            assertTrue(repairRequestsFromDB.contains(repairRequest1));
            assertFalse(repairRequestsFromDB.contains(repairRequest2));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAllByCustomerId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByCustomerId(customer.getId(), 0, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.ASC);
            assertEquals(1, repairRequestsFromDB.size());
            assertTrue(repairRequestsFromDB.contains(repairRequest1));
            assertFalse(repairRequestsFromDB.contains(repairRequest2));
            repairRequestsFromDB = repairRequestService.findAllByCustomerId(customer.getId(), 1, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.DESC);
            assertEquals(1, repairRequestsFromDB.size());
            assertTrue(repairRequestsFromDB.contains(repairRequest1));
            assertFalse(repairRequestsFromDB.contains(repairRequest2));
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
            User master = UserServiceTest.createUser(2);
            userService.insert(master);
            userService.setRoleToUser(master, UserRole.MASTER);
            master = userService.findById(master.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setMasterToRepairRequest(repairRequest1.getId(), master.getId());
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequestService.setMasterToRepairRequest(repairRequest2.getId(), master.getId());
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByMasterId(master.getId(), 0, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.DESC);
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
            repairRequestsFromDB = repairRequestService.findAllByMasterId(master.getId(), 1, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.ASC);
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertTrue(repairRequestsFromDB.contains(repairRequest2));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationAndFilteringInFindAll() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            customer1 = userService.findById(customer1.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer1.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setStatusToRepairRequest(repairRequest1.getId(), RepairRequestStatus.WAIT_FOR_PAYMENT);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer1.getId());
            repairRequestService.insert(repairRequest2);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            RepairRequest repairRequest3 = createRepairRequest(3, customer1.getId());
            repairRequestService.insert(repairRequest3);
            repairRequest3 = repairRequestService.findById(repairRequest3.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAll(0, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.DESC, RepairRequestFilterParameter.STATUS_ID, "1");
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertFalse(repairRequestsFromDB.contains(repairRequest2));
            assertTrue(repairRequestsFromDB.contains(repairRequest3));
            repairRequestsFromDB = repairRequestService.findAll(1, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.ASC, RepairRequestFilterParameter.STATUS_ID, "1");
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertFalse(repairRequestsFromDB.contains(repairRequest2));
            assertTrue(repairRequestsFromDB.contains(repairRequest3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFilteringInFindCountOfRepairRequests() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setStatusToRepairRequest(repairRequest1.getId(), RepairRequestStatus.WAIT_FOR_PAYMENT);
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            int countOfRepairRequests = repairRequestService.findCountOfRepairRequests(RepairRequestFilterParameter.STATUS_ID, "1");
            assertEquals(1, countOfRepairRequests);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFilteringInFindCountOfRepairRequestsByCustomerId() {
        try {
            User customer1 = UserServiceTest.createUser(1);
            userService.insert(customer1);
            User customer2 = UserServiceTest.createUser(2);
            userService.insert(customer2);
            RepairRequest repairRequest1 = createRepairRequest(1, customer1.getId());
            repairRequestService.insert(repairRequest1);
            RepairRequest repairRequest2 = createRepairRequest(2, customer2.getId());
            repairRequestService.insert(repairRequest2);
            RepairRequest repairRequest3 = createRepairRequest(3, customer1.getId());
            repairRequestService.insert(repairRequest3);
            repairRequestService.setStatusToRepairRequest(repairRequest3.getId(), RepairRequestStatus.WAIT_FOR_PAYMENT);
            int countOfRepairRequests = repairRequestService.findCountOfRepairRequestsByCustomerId(customer1.getId(), RepairRequestFilterParameter.STATUS_ID, "1");
            assertEquals(1, countOfRepairRequests);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingPaginationAndFilteringInFindAllByCustomerId() {
        try {
            User customer = UserServiceTest.createUser(1);
            userService.insert(customer);
            customer = userService.findById(customer.getId());
            RepairRequest repairRequest1 = createRepairRequest(1, customer.getId());
            repairRequestService.insert(repairRequest1);
            repairRequestService.setStatusToRepairRequest(repairRequest1.getId(), RepairRequestStatus.WAIT_FOR_PAYMENT);
            repairRequest1 = repairRequestService.findById(repairRequest1.getId());
            RepairRequest repairRequest2 = createRepairRequest(2, customer.getId());
            repairRequestService.insert(repairRequest2);
            repairRequest2 = repairRequestService.findById(repairRequest2.getId());
            RepairRequest repairRequest3 = createRepairRequest(3, customer.getId());
            repairRequestService.insert(repairRequest3);
            repairRequest3 = repairRequestService.findById(repairRequest3.getId());
            List<RepairRequest> repairRequestsFromDB = repairRequestService.findAllByCustomerId(customer.getId(), 0, 1, RepairRequestSortingParameter.DESCRIPTION, SortingType.DESC, RepairRequestFilterParameter.STATUS_ID, "1");
            assertEquals(1, repairRequestsFromDB.size());
            assertFalse(repairRequestsFromDB.contains(repairRequest1));
            assertFalse(repairRequestsFromDB.contains(repairRequest2));
            assertTrue(repairRequestsFromDB.contains(repairRequest3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }

    }
}

package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.entity.sorting.UserSortingParameter;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.service.base.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest extends ServiceTest {

    protected static UserService userService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ServiceTest.beforeClass();
        userService = (UserService) serviceFactory.getService(UserService.class);
    }

    public static User createUser(int number) {
        User user = new User();
        user.setFirstName("First Name " + number);
        user.setLastName("Last Name " + number);
        user.setEmail("Email " + number);
        user.setPhoneNumber("Phone number " + number);
        user.setPassword("Password " + number);
        return user;
    }

    public static boolean isUsersEquals(User firstUser, User secondUser) {
        return firstUser.getId() == secondUser.getId() &&
                firstUser.getFirstName().equals(secondUser.getFirstName()) &&
                firstUser.getLastName().equals(secondUser.getLastName()) &&
                firstUser.getEmail().equals(secondUser.getEmail()) &&
                firstUser.getPhoneNumber().equals(secondUser.getPhoneNumber()) &&
                firstUser.getPassword().equals(secondUser.getPassword()) &&
                firstUser.getRole().equals(secondUser.getRole());
    }

    @Test
    public void testInsertUser() {
        try {
            User user = createUser(1);
            userService.insert(user);
            assertNotEquals(0, user.getId());
            assertEquals(UserRole.CUSTOMER, user.getRole());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateUser() {
        try {
            User user = createUser(1);
            userService.insert(user);
            User updatedUser = createUser(2);
            updatedUser.setId(user.getId());
            updatedUser.setRole(user.getRole());
            userService.update(updatedUser);
            User updatedUserFromDB = userService.findById(updatedUser.getId());
            assertTrue(isUsersEquals(updatedUser, updatedUserFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteUser() {
        User user = createUser(1);
        try {
            userService.insert(user);
            userService.delete(user);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            userService.findById(user.getId());
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testDeleteUserWithRepairRequest() {
        User user = createUser(1);
        try {
            userService.insert(user);
            RepairRequestService repairRequestService = (RepairRequestService) serviceFactory.getService(RepairRequestService.class);
            RepairRequest repairRequest = new RepairRequest();
            repairRequest.setDescription("Description 1");
            repairRequest.setCustomerId(user.getId());
            repairRequestService.insert(repairRequest);
            userService.delete(user);
            assertEquals(0, userService.findCountOfUsersExcludeRoles());
            assertEquals(0, repairRequestService.findCountOfRepairRequests());
        } catch (DBException | NotFoundException ignored) {
            fail();
        }
    }

    @Test
    public void testFindById() {
        try {
            User user = createUser(1);
            userService.insert(user);
            User userFromDB = userService.findById(user.getId());
            assertTrue(isUsersEquals(user, userFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByIdWithNotExistedId() {
        try {
            userService.findById(1);
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindAll() {
        try {
            User user1 = createUser(1);
            userService.insert(user1);
            User user2 = createUser(2);
            userService.insert(user2);
            User user3 = createUser(3);
            userService.insert(user3);
            userService.setRoleToUser(user3, UserRole.ADMIN);
            List<User> usersFromDB = userService.findAll();
            assertEquals(2, usersFromDB.size());
            assertTrue(usersFromDB.contains(user1));
            assertTrue(usersFromDB.contains(user2));
            assertFalse(usersFromDB.contains(user3));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllWithEmptyDB() {
        try {
            List<User> usersFromDB = userService.findAll();
            assertTrue(usersFromDB.isEmpty());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetRoleToUser() {
        try {
            User user = createUser(1);
            userService.insert(user);
            userService.setRoleToUser(user, UserRole.MASTER);
            User userFromDB = userService.findById(user.getId());
            assertEquals(UserRole.MASTER, userFromDB.getRole());
            userService.setRoleToUser(user.getId(), UserRole.MANAGER);
            userFromDB = userService.findById(user.getId());
            assertEquals(UserRole.MANAGER, userFromDB.getRole());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByEmailAndPasswordWithIncorrectEmail() {
        User user = createUser(1);
        try {
            userService.insert(user);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            userService.findByEmailAndPassword(user.getEmail() + " Incorrect", user.getPassword());
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindByEmailAndPasswordWithIncorrectPassword() {
        User user = createUser(1);
        try {
            userService.insert(user);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            userService.findByEmailAndPassword(user.getEmail(), user.getPassword() + " Incorrect");
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindByEmailAndPasswordWithIncorrectEmailAndPassword() {
        User user = createUser(1);
        try {
            userService.insert(user);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
        try {
            userService.findByEmailAndPassword(user.getEmail() + " Incorrect", user.getPassword() + " Incorrect");
            fail();
        } catch (DBException e) {
            fail(e.getMessage());
        } catch (NotFoundException ignored) {
        }
    }

    @Test
    public void testFindByEmailAndPassword() {
        try {
            User user = createUser(1);
            userService.insert(user);
            User userFromDB = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
            assertTrue(isUsersEquals(user, userFromDB));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByRole() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            userCustomer = userService.findById(userCustomer.getId());
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            userManager = userService.findById(userManager.getId());
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            userMaster = userService.findById(userMaster.getId());
            List<User> usersFromDB = userService.findAllByRole(UserRole.CUSTOMER);
            assertEquals(1, usersFromDB.size());
            assertTrue(isUsersEquals(userCustomer, usersFromDB.get(0)));
            usersFromDB = userService.findAllByRole(UserRole.MANAGER);
            assertEquals(1, usersFromDB.size());
            assertTrue(isUsersEquals(userManager, usersFromDB.get(0)));
            usersFromDB = userService.findAllByRole(UserRole.MASTER);
            assertEquals(1, usersFromDB.size());
            assertTrue(isUsersEquals(userMaster, usersFromDB.get(0)));
            usersFromDB = userService.findAllByRole(UserRole.ADMIN);
            assertTrue(usersFromDB.isEmpty());
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAllByRole() {
        try {
            User userCustomer1 = createUser(1);
            userService.insert(userCustomer1);
            userService.setRoleToUser(userCustomer1.getId(), UserRole.CUSTOMER);
            userCustomer1 = userService.findById(userCustomer1.getId());
            User userCustomer2 = createUser(2);
            userService.insert(userCustomer2);
            userService.setRoleToUser(userCustomer2.getId(), UserRole.CUSTOMER);
            userCustomer2 = userService.findById(userCustomer2.getId());
            User userCustomer3 = createUser(3);
            userService.insert(userCustomer3);
            userService.setRoleToUser(userCustomer3.getId(), UserRole.CUSTOMER);
            userCustomer3 = userService.findById(userCustomer3.getId());
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            userAdmin = userService.findById(userAdmin.getId());
            List<User> usersFromDB = userService.findAllByRole(UserRole.CUSTOMER, 0, 2, UserSortingParameter.FIRST_NAME, SortingType.DESC);
            assertEquals(2, usersFromDB.size());
            assertFalse(usersFromDB.contains(userAdmin));
            assertFalse(usersFromDB.contains(userCustomer1));
            assertTrue(isUsersEquals(userCustomer3, usersFromDB.get(0)));
            assertTrue(isUsersEquals(userCustomer2, usersFromDB.get(1)));
            usersFromDB = userService.findAllByRole(UserRole.CUSTOMER, 1, 2, UserSortingParameter.LAST_NAME, SortingType.ASC);
            assertEquals(2, usersFromDB.size());
            assertFalse(usersFromDB.contains(userAdmin));
            assertFalse(usersFromDB.contains(userCustomer1));
            assertTrue(isUsersEquals(userCustomer2, usersFromDB.get(0)));
            assertTrue(isUsersEquals(userCustomer3, usersFromDB.get(1)));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllExcludeRolesWithOneExcludedRole() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            userCustomer = userService.findById(userCustomer.getId());
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            userManager = userService.findById(userManager.getId());
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            userMaster = userService.findById(userMaster.getId());
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            userAdmin = userService.findById(userAdmin.getId());
            List<User> usersFromDB = userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN}, 0, 3, UserSortingParameter.CREATED_AT, SortingType.DESC);
            assertEquals(3, usersFromDB.size());
            assertTrue(usersFromDB.contains(userCustomer));
            assertTrue(usersFromDB.contains(userManager));
            assertTrue(usersFromDB.contains(userMaster));
            assertFalse(usersFromDB.contains(userAdmin));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllExcludeRolesWithMoreThenOneExcludedRole() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            userCustomer = userService.findById(userCustomer.getId());
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            userManager = userService.findById(userManager.getId());
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            userMaster = userService.findById(userMaster.getId());
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            userAdmin = userService.findById(userAdmin.getId());
            List<User> usersFromDB = userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN, UserRole.MASTER}, 0, 2, UserSortingParameter.CREATED_AT, SortingType.DESC);
            assertEquals(2, usersFromDB.size());
            assertTrue(usersFromDB.contains(userCustomer));
            assertTrue(usersFromDB.contains(userManager));
            assertFalse(usersFromDB.contains(userMaster));
            assertFalse(usersFromDB.contains(userAdmin));
            usersFromDB = userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN, UserRole.MASTER, UserRole.MANAGER}, 0, 1, UserSortingParameter.CREATED_AT, SortingType.DESC);
            assertEquals(1, usersFromDB.size());
            assertTrue(usersFromDB.contains(userCustomer));
            assertFalse(usersFromDB.contains(userManager));
            assertFalse(usersFromDB.contains(userMaster));
            assertFalse(usersFromDB.contains(userAdmin));
            usersFromDB = userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN, UserRole.MASTER, UserRole.MANAGER, UserRole.CUSTOMER}, 0, 0, UserSortingParameter.CREATED_AT, SortingType.DESC);
            assertEquals(0, usersFromDB.size());
            assertFalse(usersFromDB.contains(userCustomer));
            assertFalse(usersFromDB.contains(userManager));
            assertFalse(usersFromDB.contains(userMaster));
            assertFalse(usersFromDB.contains(userAdmin));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllExcludeRolesWithEmptyArrayOfExcludedRoles() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            userCustomer = userService.findById(userCustomer.getId());
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            userManager = userService.findById(userManager.getId());
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            userMaster = userService.findById(userMaster.getId());
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            userAdmin = userService.findById(userAdmin.getId());
            List<User> usersFromDB = userService.findAllExcludeRoles(new UserRole[]{}, 0, 4, UserSortingParameter.CREATED_AT, SortingType.DESC);
            assertEquals(4, usersFromDB.size());
            assertTrue(usersFromDB.contains(userCustomer));
            assertTrue(usersFromDB.contains(userManager));
            assertTrue(usersFromDB.contains(userMaster));
            assertTrue(usersFromDB.contains(userAdmin));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingAndPaginationInFindAllExcludeRolesWithOneExcludedRole() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            userCustomer = userService.findById(userCustomer.getId());
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            userManager = userService.findById(userManager.getId());
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            userMaster = userService.findById(userMaster.getId());
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            userAdmin = userService.findById(userAdmin.getId());
            List<User> usersFromDB = userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN}, 0, 2, UserSortingParameter.FIRST_NAME, SortingType.ASC);
            assertEquals(2, usersFromDB.size());
            assertFalse(usersFromDB.contains(userAdmin));
            assertFalse(usersFromDB.contains(userMaster));
            assertTrue(isUsersEquals(userCustomer, usersFromDB.get(0)));
            assertTrue(isUsersEquals(userManager, usersFromDB.get(1)));
            usersFromDB = userService.findAllExcludeRoles(new UserRole[]{UserRole.ADMIN}, 1, 2, UserSortingParameter.LAST_NAME, SortingType.DESC);
            assertEquals(2, usersFromDB.size());
            assertFalse(usersFromDB.contains(userAdmin));
            assertFalse(usersFromDB.contains(userMaster));
            assertTrue(isUsersEquals(userManager, usersFromDB.get(0)));
            assertTrue(isUsersEquals(userCustomer, usersFromDB.get(1)));
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindMasterById() {
        try {
            User userMaster = createUser(1);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            userMaster = userService.findById(userMaster.getId());
            User userMasterFromDB = userService.findMasterById(userMaster.getId());
            assertTrue(isUsersEquals(userMaster, userMasterFromDB));
            userService.setRoleToUser(userMaster.getId(), UserRole.MANAGER);
            try {
                userService.findMasterById(userMaster.getId());
                fail();
            } catch (NotFoundException ignored) {
            }
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCustomerById() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userCustomer = userService.findById(userCustomer.getId());
            User userCustomerFromDB = userService.findCustomerById(userCustomer.getId());
            assertTrue(isUsersEquals(userCustomer, userCustomerFromDB));
            userService.setRoleToUser(userCustomer.getId(), UserRole.MANAGER);
            try {
                userService.findCustomerById(userCustomer.getId());
                fail();
            } catch (NotFoundException ignored) {
            }
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfUsersExcludeRolesWithEmptyArrayOfRoles() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            int countOfUsers = userService.findCountOfUsersExcludeRoles();
            assertEquals(4, countOfUsers);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfUsersExcludeRolesWithOneExcludedRole() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            int countOfUsers = userService.findCountOfUsersExcludeRoles(UserRole.ADMIN);
            assertEquals(3, countOfUsers);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfUsersExcludeRolesWithMoreThenOneExcludedRole() {
        try {
            User userCustomer = createUser(1);
            userService.insert(userCustomer);
            userService.setRoleToUser(userCustomer.getId(), UserRole.CUSTOMER);
            User userManager = createUser(2);
            userService.insert(userManager);
            userService.setRoleToUser(userManager.getId(), UserRole.MANAGER);
            User userMaster = createUser(3);
            userService.insert(userMaster);
            userService.setRoleToUser(userMaster.getId(), UserRole.MASTER);
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            int countOfUsers = userService.findCountOfUsersExcludeRoles(UserRole.ADMIN, UserRole.MASTER);
            assertEquals(2, countOfUsers);
            countOfUsers = userService.findCountOfUsersExcludeRoles(UserRole.ADMIN, UserRole.MASTER, UserRole.MANAGER);
            assertEquals(1, countOfUsers);
            countOfUsers = userService.findCountOfUsersExcludeRoles(UserRole.ADMIN, UserRole.MASTER, UserRole.MANAGER, UserRole.CUSTOMER);
            assertEquals(0, countOfUsers);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindCountOfUsersByRole() {
        try {
            User userCustomer1 = createUser(1);
            userService.insert(userCustomer1);
            userService.setRoleToUser(userCustomer1.getId(), UserRole.CUSTOMER);
            User userCustomer2 = createUser(2);
            userService.insert(userCustomer2);
            userService.setRoleToUser(userCustomer2.getId(), UserRole.CUSTOMER);
            User userCustomer3 = createUser(3);
            userService.insert(userCustomer3);
            userService.setRoleToUser(userCustomer3.getId(), UserRole.CUSTOMER);
            User userAdmin = createUser(4);
            userService.insert(userAdmin);
            userService.setRoleToUser(userAdmin.getId(), UserRole.ADMIN);
            int countOfUsers = userService.findCountOfUsersByRole(UserRole.CUSTOMER);
            assertEquals(3, countOfUsers);
            countOfUsers = userService.findCountOfUsersByRole(UserRole.ADMIN);
            assertEquals(1, countOfUsers);
            countOfUsers = userService.findCountOfUsersByRole(UserRole.MASTER);
            assertEquals(0, countOfUsers);
        } catch (DBException | NotFoundException e) {
            fail(e.getMessage());
        }
    }
}

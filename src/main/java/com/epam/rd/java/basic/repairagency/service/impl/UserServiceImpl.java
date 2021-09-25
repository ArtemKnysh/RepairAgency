package com.epam.rd.java.basic.repairagency.service.impl;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.entity.sorting.UserSortingParameter;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Service;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.repository.UserRepository;
import com.epam.rd.java.basic.repairagency.service.UserService;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service(UserService.class)
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Inject
    private UserRepository repository;

    private UserServiceImpl() {
    }

    @Override
    protected GenericRepository<User> getRepository() {
        return repository;
    }

    @Override
    public void setRoleToUser(User user, UserRole role) throws DBException {
        setRoleToUser(user.getId(), role);
    }

    @Override
    public void setRoleToUser(long userId, UserRole role) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.setRoleToUser(connection, userId, role);
        } catch (SQLException e) {
            throw new DBException("Can't set role " + role + " to user with id '" + userId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findByEmailAndPassword(connection, email, password);
        } catch (SQLException e) {
            throw new DBException("Can't find user by email '" + email + "' and password", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<User> findAllByRole(UserRole role) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByRole(connection, role);
        } catch (SQLException e) {
            throw new DBException("Can't find users with role '" + role + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<User> findAllByRole(UserRole role, int offset, int amount,
                                    UserSortingParameter sortingParam, SortingType sortingType) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByRole(connection, role, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find users with role '" + role + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<User> findAllExcludeRoles(UserRole[] roles, int offset, int amount,
                                          UserSortingParameter sortingParam, SortingType sortingType
    ) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllExcludeRoles(connection, roles, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find users without roles '" + Arrays.toString(roles) + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public User findMasterById(long masterId) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            User master = repository.findById(connection, masterId);
            if (master.getRole() != UserRole.MASTER) {
                throw new NotFoundException("Requested user with id '" + masterId + "' isn't a master");
            }
            return master;
        } catch (SQLException e) {
            throw new DBException("Can't find master with id '" + masterId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public User findCustomerById(long customerId) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            User master = repository.findById(connection, customerId);
            if (master.getRole() != UserRole.CUSTOMER) {
                throw new NotFoundException("Requested user with id '" + customerId + "' isn't a customer");
            }
            return master;
        } catch (SQLException e) {
            throw new DBException("Can't find customer with id '" + customerId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfUsersByRole(UserRole role) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfUsersByRole(connection, role);
        } catch (SQLException e) {
            throw new DBException("Can't find count of users by role '" + role + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfUsersExcludeRoles(UserRole... roles) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfUsersExcludeRoles(connection, roles);
        } catch (SQLException e) {
            throw new DBException("Can't find count of users", e);
        } finally {
            DBUtil.close(connection);
        }
    }
}

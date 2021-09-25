package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.entity.sorting.UserSortingParameter;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.util.List;

public interface UserService extends GenericService<User> {

    void setRoleToUser(User user, UserRole role) throws DBException;

    void setRoleToUser(long userId, UserRole role) throws DBException;

    User findByEmailAndPassword(String email, String password) throws NotFoundException, DBException;

    List<User> findAllByRole(UserRole role) throws DBException, NotFoundException;

    List<User> findAllByRole(UserRole role, int offset, int amount,
                             UserSortingParameter sortingParam, SortingType sortingType) throws NotFoundException, DBException;

    User findMasterById(long masterId) throws NotFoundException, DBException;

    User findCustomerById(long customerId) throws NotFoundException, DBException;

    int findCountOfUsersByRole(UserRole role) throws DBException;

    List<User> findAllExcludeRoles(UserRole[] roles, int offset, int amount, UserSortingParameter userSortingParameter,
                                   SortingType sortingType) throws DBException, NotFoundException;

    int findCountOfUsersExcludeRoles(UserRole... roles) throws DBException;
}

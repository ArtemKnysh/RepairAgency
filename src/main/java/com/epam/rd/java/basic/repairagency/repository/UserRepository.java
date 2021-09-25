package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.UserRole;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.entity.sorting.UserSortingParameter;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends GenericRepository<User> {

    void setRoleToUser(Connection connection, User user, UserRole role) throws SQLException;

    void setRoleToUser(Connection connection, long userId, UserRole role) throws SQLException;

    User findByEmailAndPassword(Connection connection, String email, String password) throws NotFoundException, SQLException;

    List<User> findAllByRole(Connection connection, UserRole role) throws SQLException, NotFoundException;

    List<User> findAllByRole(Connection connection, UserRole role, int offset, int amount,
                             UserSortingParameter sortingParam, SortingType sortingType) throws SQLException, NotFoundException;

    List<User> findAllExcludeRoles(Connection connection, UserRole[] roles, int offset, int amount,
                                   UserSortingParameter sortingParam, SortingType sortingType) throws SQLException, NotFoundException;

    int findCountOfUsersByRole(Connection connection, UserRole role) throws SQLException;

    int findCountOfUsersExcludeRoles(Connection connection, UserRole... roles) throws SQLException;
}
